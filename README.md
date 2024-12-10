# cripto-prices-api
**Projeto para consulta de preços de criptomoedas**

Para rodar o projeto com Docker:
```console
docker build -t cripto-prices-api .
```
```console
docker run -p 8080:8080 cripto-prices-api
```

Pra rodar o projeto sem criar várias imagens e containers no Docker:
```console
chmod +x rebuild.sh
```
```console
./rebuild.sh
```