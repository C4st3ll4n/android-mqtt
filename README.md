# Introdução
Este projeto é parte de um estudo sobre possibilidades de implementação de serviço de push na Lio.
Aqui foi implementado um cliente MQTT que se conecta no tópico "lio/inbox". Do lado servidor
foi usado o RabbitMQ com o plugin MQTT, e as mensagem são postadas na fila "amq.topic", usando routing key
"lio.inbox"

# RabbitMQ

No diretório rabbitmq estão os arquivos para executar o rabbitmq localmente via docker.
Só é necessário entrar no diretório e executar:

```sh
docker-compose up
```