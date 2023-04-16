# Petshop

Sistema de controle de atendimentos de uma petshop.

## Como rodar o projeto

### Pré-requisitos
- Java 17 ou superior
- Maven
- PostgreSQL

### Configuração
Configurar as chaves de sistema:
```
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_DB=
POSTGRES_PORT=
```

### Executar o Projeto
Dentro do terminal bash rode
```
./mvnw spring-boot:run
```

## Conceitos utilizados
- [MVC Pattern](https://pt.wikipedia.org/wiki/MVC)
- [The Twelve Factor App](https://12factor.net/pt_br/)
- [JWT Stateless Authentication](https://jwt.io/)
