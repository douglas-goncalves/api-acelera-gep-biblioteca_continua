![Logo](https://github.com/douglas-goncalves/acelera_assets/blob/master/logo.jpeg)
# Api aceleraGep Biblioteca
[![shield-mit](https://img.shields.io/badge/license-MIT-green)](https://github.com/douglas-goncalves/acelera_assets/blob/master/docs/LICENCE)
# Sobre o Projeto
Este é o primeiro projeto de avaliação do Acelera ao qual o tema é biblioteca.

# Tecnologias Utilizadas
- Spring boot
- Hibernate
- Mysql
- Swagger
- Java 17

# Backend
### Pré-requisitos:
- Java JDK 17 ( OpenJDK17 )
- Framework ( SpringTools ) 

# Como importar e levantar o projeto

 1. Clonar Repositório
```Banch
git clone https://github.com/douglas-goncalves/api-acelera-biblioteca.git
```
 2. Abrir o SpringTools, Eclipse ou outro de sua preferência e que tenha suporte.
 3. Importar o projeto.
 4. Será Necessário Configurar o application.properties do projeto ou definir algumas variáveis de sistema:
 ![application_properties1](https://github.com/douglas-goncalves/acelera_assets/blob/master/application-properties.png)
 
 #### Host, Port, Schema, User e Password
  ```Banch
  DATABASE_HOST
  ```
  ```Banch
  DATABASE_PORT
  ```
  ```Banch
  DATABASE_NAME
  ```
  ```Banch
  DATABASE_USER
  ```
  ```Banch
  DATABASE_PASSWORD 
  ```
   - Um Exemplo para caso resolva não usar as variaveis de sistema
 ![application_properties2](https://github.com/douglas-goncalves/acelera_assets/blob/master/application-properties_exemplo.png)
  
 ## Requisições e Objetos Json usadas no Postman:
 
 #### Link do Json exportado pelo Postman contendo as requisições:
 <https://github.com/douglas-goncalves/acelera_assets/blob/master/docs/AceleraGepBiblioteca.postman_collection.json>
  
### AutorEntity:
#### Lista todos:
 ```Banch
http://localhost:8080/api/autores?page=0&size=10&sort=id,asc
```
Busca pelo id:
 ```Banch
http://localhost:8080/api/autores/{idAutor}
```
Deleta: 
 ```Banch
http://localhost:8080/api/autores/{idAutor}
```
Cadastra:
 ```Banch
http://localhost:8080/api/autores
```
- Corpo da requisição:
~~~json
{
	"nome":"String",
	"bibliografia":"String"
}
~~~

Atualiza:
 ```Banch
http://localhost:8080/api/autores/{idAutor}
```
- Corpo da requisição:
~~~json
{
	"nome":"String",
	"bibliografia":"String"
}
~~~

Lista os Livro pelo id do autor:
 ```Banch
http://localhost:8080/api/autores/{idAutor}/livros?page=0&size=10&sort=id,asc
```
### LivroEntity:
#### Lista todos: 
 ```Banch
http://localhost:8080/api/livros?page=0&size=10&sort=id,asc
```
Busca pelo id: 
 ```Banch
http://localhost:8080/api/livros/{idLivro}
```
Deleta: 
 ```Banch
http://localhost:8080/api/livros/{idLivro}
```
Cadastra: 
 ```Banch
http://localhost:8080/api/livros
```
- Corpo da requisição:
~~~json
{
	"titulo":"String",
	"anoLancamento":"Int",
	"autores":[1,2,3,...N]
}
~~~
Atualiza: 
 ```Banch
http://localhost:8080/api/livros/{idLivro}
```
- Corpo da requisição:
~~~json
{
	"titulo":"String",
	"anoLancamento":"Int",
	"autores":[1,2,3,...N]
}
~~~
# Swagger
### O Swagger esta incorporado ao projeto usando Dependencias (**springdoc-openapi-ui** e **springdoc-openapi-data-rest**) no pom.xml
Para Acessar a documentação será necessario levantar a aplicação e usar o URL a baixo para acessá-la.
- <http://localhost:8080/swagger-ui/index.html#/>
#### Obs: O link poderá variar um pouco de PC para PC dependendo das configurações, um exemplo é que a porta usada neste caso é 8080 e em outros casos poderá não ser a  mesma.




# Modelo Entidade Relacionameto:
![mer](https://github.com/douglas-goncalves/acelera_assets/blob/master/mer.png)

### Link do sql usado para popular as tabelas para alguns testes:
<https://github.com/douglas-goncalves/acelera_assets/blob/master/docs/popular_tabelas_aceleragep_biblioteca.sql>



# Auto
Douglas Goncalves
