### Commands to set up PostgreSQL on localhost:5432
1. `cd db` (If not already in the directory)
1. `docker build -t my_psql .`
1. `docker run --rm -d -p 5432:5432 --name my_psql_instance my_psql`

### Test the database setup:
1. `psql -h localhost -p 5432 -d appdb -U root`
1. Enter password (`root` for now), then we should have psql SSL connection to the database.
