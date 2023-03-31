# Petshop

Sistema de controle de atendimentos de uma petshop.

<!-- Connect Database (java -cp  /home/cazevedo/.m2/repository/org/hsqldb/hsqldb/2.7.1/hsqldb-2.7.1.jar org.hsqldb.util.DatabaseManagerSwing) -->


## Acessar o Banco de Dados

O projeto está utilizando o H2.

- [localhost:8080/h2-console/](localhost:8080/h2-console/)

com credenciais:

- **user**: sa
- **password**: asdf

## Rotas

### Raças

| Link | Verbo HTTP | O que faz? |
| :--  | :-:        | :-         |
| http://localhost:8080/races        | GET    | Recebe todas as raças de Pet          |
| http://localhost:8080/races/\{id\} | GET    | Recebe uma Raça de Pet com \{id\}     |
| http://localhost:8080/races        | POST   | Cria uma Raça de Pet (requisição)     |
| http://localhost:8080/races/\{id\} | PUT    | Atualiza uma Raça de Pet (requisição) |
| http://localhost:8080/races/\{id\} | DELETE | Deleta uma Raça de Pet                |

### Endereços

| Link | Verbo HTTP | O que faz? |
| :--  | :-:        | :-         |
| http://localhost:8080/addresses        | GET    | Recebe todas os endereços         |
| http://localhost:8080/addresses/\{id\} | GET    | Recebe um endereço com \{id\}     |
| http://localhost:8080/addresses        | POST   | Cria um endereço (requisição)     |
| http://localhost:8080/addresses/\{id\} | PUT    | Atualiza um endereço (requisição) |
| http://localhost:8080/addresses/\{id\} | DELETE | Deleta uma endereço               |


## Requisições POST

### Criar um novo registro de Raça
```
URL: http://localhost:8080/races
Method: POST
Headers: 
    Content-Type: application/json
Body:
{
    "description": "Labrador Retriever"
}
```

### Atualizar o registro de uma Raça
```
URL: http://localhost:8080/races/1
Method: PUT
Headers: 
    Content-Type: application/json
Body:
{
    "description": "Collie"
}
```

### Criar um novo registro de Endereço
```
URL: POST /addresses
Headers:
	Content-Type: application/json
Body:
{
	"addressLine": "123 Main St",
	"avenue": "Central",	
	"city": "Anytown",
	"state": "CA",
	"country": "USA",
	"zipCode": "12345"
}
```

### Atualizar o registro de um Endereço

```
URL: PUT /addresses/1
Headers:
	Content-Type: application/json
Body:
{
	"addressLine": "123 Main St",
	"avenue": "Central",
	"city": "Anytown",
	"state": "CA",
	"country": "USA",
	"zipCode": "12345",
	"additionalInformation": "Suite 100, enter through the back door."
}
```

