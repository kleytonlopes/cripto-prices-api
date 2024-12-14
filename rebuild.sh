#!/bin/bash

# Remove o container antigo
docker rm -f cripto-prices-api-container

# Remove a imagem antiga (opcional)
docker rmi cripto-prices-api

# Construir uma nova imagem
docker build -t cripto-prices-api .

# Executar um novo container
docker run --name cripto-prices-api-container --env-file .env -p 8080:8080 cripto-prices-api
