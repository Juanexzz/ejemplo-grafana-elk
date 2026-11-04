# Ejemplo Spring Boot con Grafana, Prometheus y Stack ELK (Reto 7)

Este repositorio contiene una aplicación Spring Boot 3.5 con observabilidad completa mediante dos stacks de Docker Compose:
- **Stack de Monitoreo (`monitoring/`)**: Prometheus (`9090`) y Grafana (`3100` -> `3000`).
- **Stack de Logs (`elk-docker-compose/`)**: Elasticsearch (`9200`), Logstash (`5001` -> `5000`) y Kibana (`5601`).

---

## Evidencias Reto 7

### 1. Comandos para Levantar Todo

#### A. Iniciar los servicios de monitoreo (Prometheus y Grafana)
```bash
cd monitoring
docker compose up -d
cd ..
```

#### B. Iniciar el stack de logs (ELK: Elasticsearch, Logstash, Kibana)
```bash
cd elk-docker-compose
docker compose up -d
cd ..
```

#### C. Iniciar la aplicación Spring Boot (en segundo plano o en una terminal separada)
```bash
./mvnw spring-boot:run
```
> En Windows PowerShell también se puede usar `./mvnw.cmd spring-boot:run`.

#### D. Generar tráfico de prueba (métricas y logs)
Ejecutar peticiones a los endpoints `/api/saludo` (genera log nivel INFO) y `/api/despedida` (genera log nivel ERROR):

**Bash / Linux / macOS:**
```bash
for i in {1..30}; do
  curl -s http://localhost:8080/api/saludo > /dev/null
  curl -s http://localhost:8080/api/despedida > /dev/null
done
```

**PowerShell (Windows):**
```powershell
1..30 | ForEach-Object {
    Invoke-WebRequest -Uri "http://localhost:8080/api/saludo" -UseBasicParsing | Out-Null
    Invoke-WebRequest -Uri "http://localhost:8080/api/despedida" -UseBasicParsing | Out-Null
}
```

---

### 2. Tabla de URLs Reales de los Servicios

| Servicio | URL Real | Puerto Host | Descripción / Credenciales |
| :--- | :--- | :--- | :--- |
| **Spring Boot App** | [http://localhost:8080](http://localhost:8080) | `8080` | Endpoint base de la API |
| **API Saludo (INFO)** | [http://localhost:8080/api/saludo](http://localhost:8080/api/saludo) | `8080` | Genera log `INFO: Saludando` |
| **API Despedida (ERROR)** | [http://localhost:8080/api/despedida](http://localhost:8080/api/despedida) | `8080` | Genera log `ERROR: Despidiendo` |
| **Actuator Health** | [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) | `8080` | Estado de salud de la aplicación |
| **Actuator Prometheus** | [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus) | `8080` | Endpoint de métricas de Prometheus |
| **Prometheus Web UI** | [http://localhost:9090](http://localhost:9090) | `9090` | Consola web de Prometheus |
| **Prometheus Targets** | [http://localhost:9090/targets](http://localhost:9090/targets) | `9090` | Estado del target `MetricasGestion` (`UP`) |
| **Grafana Web UI** | [http://localhost:3100](http://localhost:3100) | `3100` | Dashboard y métricas (puerto `3100` en host; credenciales: `admin` / `admin`) |
| **Elasticsearch API** | [http://localhost:9200](http://localhost:9200) | `9200` | Información del cluster Elasticsearch |
| **Elasticsearch Índices** | [http://localhost:9200/_cat/indices?v](http://localhost:9200/_cat/indices?v) | `9200` | Lista de índices generados (`gestion-configuracion-app-*`) |
| **Kibana Web UI** | [http://localhost:5601](http://localhost:5601) | `5601` | Interfaz web para visualización de logs |
| **Logstash TCP Input** | `localhost:5001` | `5001` | Socket TCP (mapeado al `5000` interno del contenedor) para logs JSON |

> **Nota sobre el puerto de Grafana (3100)**: En Windows, el rango dinámico de puertos `2923 - 3022` suele estar reservado por Windows NAT / Hyper-V (`winnat`), lo que bloquea el bind directo al puerto `3000` en el host. Por ello, Grafana está expuesto en el puerto host `3100` (`3100:3000`).

---

### 3. Comandos de Validación de Evidencias

- **Contenedores activos en Docker:**
  ```bash
  docker ps
  ```
- **Métricas Prometheus desde Actuator:**
  ```bash
  curl http://localhost:8080/actuator/prometheus
  ```
- **Estado del target en Prometheus API:**
  ```bash
  curl http://localhost:9090/api/v1/targets
  ```
- **Estado e índices en Elasticsearch:**
  ```bash
  curl http://localhost:9200
  curl "http://localhost:9200/_cat/indices?v"
  ```
