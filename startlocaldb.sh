export MYSQL_DB_ADMIN_USERNAME=root
export MYSQL_DB_ADMIN_PASSWORD=password
export MYSQL_DB_URL=jdbc:mysql://localhost:3306

export POSTGRES_DB_ADMIN_USERNAME=root
export POSTGRES_DB_ADMIN_PASSWORD=password
export POSTGRES_DB_URL=jdbc:postgresql://localhost:5432/

mkdir -p ./data/mysqldata
docker-compose up -d

while ! curl http://localhost:5432/ 2>&1 | grep '52'
do
  sleep 20s
done