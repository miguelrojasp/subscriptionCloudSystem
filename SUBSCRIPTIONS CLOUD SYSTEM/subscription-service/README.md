# Subscription Service

## REST API

| Endpoint	      			| Method   | Req. body  		| Status | Resp. body     			| Description    		   	     					|
|:-------------------------:|:--------:|:------------------:|:------:|:------------------------:|:--------------------------------------------------|
| `/subscriptions`        	| `GET`    |            		| 200    | Iterable<Subscription> 	| Get all the subscriptions in the catalog. 		|
| `/subscriptions`        	| `POST`   | Subscription       | 201    | Subscription           	| Add a new subscription to the catalog. 			|
|                 			|          |            		| 422    |                			| A subscription with the same EMAIL already exists. |
| `/subscriptions/{email}` 	| `GET`    |            		| 200    | Subscription           	| Get the subscription with the given EMAIL. 		|
|                 			|          |            		| 404    |                			| No subscription with the given EMAIL exists. 		|
| `/subscriptions/{email}` 	| `PUT`    | Subscription       | 200    | Subscription           	| Update the subscription with the given EMAIL. 		|
|                 			|          |            		| 201    | Subscription           	| Create a subscription with the given EMAIL. 		|
| `/subscriptions/{email}` 	| `DELETE` |            		| 204    |                			| Delete the subscription with the given EMAIL. 		|
|                 			|          |            		| 404    |                			| No subscription with the given EMAIL exists. 		|

## Useful Commands

| Gradle Command	   | Description            |
|:--------------------:|:----------------------:|
| `./gradlew bootRun`  | Run the application.   |
| `./gradlew build`    | Build the application. |
| `./gradlew test`     | Run tests.             |

After building the application, you can also run it from the Java CLI:

```bash
java -jar build/libs/subscription-service-0.0.1-SNAPSHOT.jar
```

## Running a PostgreSQL Database (Docker)

Run PostgreSQL as a Docker container:

```bash
docker run --name polardb-catalog -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=polardb_catalog -p 5432:5432 -d postgres:13
```

### Container Commands

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop polardb-catalog`   | Stop container.   |
| `docker start polardb-catalog`  | Start container.  |
| `docker remove polardb-catalog` | Remove container. |

### Database Commands

Start an interactive PSQL console:

```bash
docker exec -it polardb-catalog psql -U admin -d polardb_catalog
```

| PSQL Command	             | Description                    |
|:--------------------------:|:------------------------------:|
| `\list`                    | List all databases.            |
| `\connect polardb_catalog` | Connect to specific database.  |
| `\dt`                      | List all tables.               |
| `\quit`                    | Quit interactive psql console. |

## Running a PostgreSQL Database (Kubernetes)

Run PostgreSQL as a Helm chart:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

```bash
helm install polardb-catalog bitnami/postgresql \
  --set postgresqlUsername=admin \
  --set postgresqlPassword=admin \
  --set postgresqlDatabase=polardb_catalog \
  --set image.tag=13 \
  --set service.port=5432
```
