package sd.web.server.db

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database.Companion.connectPool
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import sd.web.server.data.*
import sd.web.server.db.tables.HomeworkCheckers
import sd.web.server.db.tables.Checker as CheckerTable
import sd.web.server.db.tables.Homework as HomeworkTable
import sd.web.server.db.tables.Submission as SubmissionTable
import sd.web.server.db.tables.SubmissionCheck as SubmissionCheckTable

class DBService : KoinComponent {
    private val db = connectPool(getKoin().get())

    private fun <T : IntIdTable> T.singleInsert(body: T.(InsertStatement<EntityID<Int>>) -> Unit): Int {
        return transaction(db) { insertAndGetId(body).value }
    }

    fun addSubmission(submission: Submission): Int = SubmissionTable.singleInsert {
        it[homeworkId] = submission.homeworkId
        it[time] = submission.time
        it[solution] = submission.solution
    }

    fun addHomework(homework: Homework): Int = HomeworkTable.singleInsert {
        it[title] = homework.title
        it[publicationTime] = homework.publicationTime
        it[deadline] = homework.deadline
        it[description] = homework.description
    }

    fun addChecker(checker: Checker): Int = CheckerTable.singleInsert {
        it[command] = checker.command
    }

    fun addSubmissionCheck(submissionCheck: SubmissionCheck): Int = SubmissionCheckTable.singleInsert {
        it[submissionId] = submissionCheck.submissionId
        it[checkerId] = submissionCheck.checkerId
        it[mark] = submissionCheck.mark
        it[output] = submissionCheck.output
    }

    fun addCheckerToHomework(homeworkId: Int, checkerId: Int) = transaction(db) {
        HomeworkCheckers.insert {
            it[this.homeworkId] = homeworkId
            it[this.checkerId] = checkerId
        }
    }

    fun homeworks(): List<HomeworkWithId> = transaction(db) {
        HomeworkTable.selectAll().map { it.asHomeworkWithId }
    }

    fun homework(id: Int): Homework? = transaction(db) {
        HomeworkTable.select { HomeworkTable.id eq id }.firstOrNull()?.asHomework
    }

    fun submission(id: Int): Submission? = transaction(db) {
        SubmissionTable.select { SubmissionTable.id eq id }.firstOrNull()?.asSubmission
    }

    fun submissions(): List<SubmissionWithId> = transaction(db) {
        SubmissionTable.selectAll().map { it.asSubmissionWithId }
    }

    fun checker(id: Int): Checker? = transaction(db) {
        CheckerTable.select { CheckerTable.id eq id }.firstOrNull()?.asChecker
    }

    fun homeworkSubmissions(homeworkId: Int): List<SubmissionWithId> = transaction(db) {
        SubmissionTable.select { SubmissionTable.homeworkId eq homeworkId }.map { it.asSubmissionWithId }
    }

    fun homeworkCheckers(homeworkId: Int): List<CheckerWithId> = transaction(db) {
        HomeworkCheckers.select { HomeworkCheckers.homeworkId eq homeworkId }.map { it.asCheckerWithId }
    }

    fun submissionChecks(submissionId: Int): List<SubmissionCheck> = transaction(db) {
        SubmissionCheckTable.select { SubmissionCheckTable.submissionId eq submissionId }.map { it.asSubmissionCheck }
    }
}
