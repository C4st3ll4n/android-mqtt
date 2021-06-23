# Introdução
Este projeto é parte de um estudo sobre possibilidades de implementação de serviço de push na Lio.
Aqui foi implementado um cliente MQTT que se conecta no tópico `lio/inbox`. Do lado servidor
foi usado o RabbitMQ com o plugin MQTT, e as mensagem são postadas na exchange `amq.topic`, usando routing key
`lio.inbox`

# RabbitMQ

No diretório rabbitmq estão os arquivos para executar o rabbitmq localmente via docker.
Para subir o serviço já com as configurações:

```sh
docker-compose up
```

Também é necessário editar no app o endereço do servidor local na parte do código onde é feita a conexão.

# Enviando mensagens

se a mensagem for postada na exchange `amq.topic` com routing key `lio.inbox` ela deverá chegar na Lio. Uma das
formas é pelo console do RabbitMQ, descrita abaixo:

- Abra o console do RabbitMQ (http://localhost:15672/)
- Credenciais: guest/guest
- Vá em exchanges e selecione `amq.topic`
- Na tela que abrir haverá uma seção chamada `Publish message`
- Preencha o campo `Routing key` com `lio.inbox` e preencha o payload livremente
- Clique em `Publish message` e ela deve aparecer na Lio, demonstrando o conceito.