createdb -h localhost -p 5432 sd-web
psql -d sd-web -a -f server/src/main/sql/init.sql
