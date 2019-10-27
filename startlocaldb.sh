mkdir -p ./data/mysqldata
docker-compose up -d

while ! curl http://localhost:5432/ 2>&1 | grep '52'
do
  sleep 20s
done