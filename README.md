# 📄 Proyecto Puntored – API de Recargas

Este proyecto implementa un servicio de recargas utilizando **Spring Boot** y consumiendo los servicios de **Puntored**.  
Incluye persistencia de datos y un frontend en **React** para la interacción con el usuario.

---

## 🚀 Requisitos previos
> **Nivel:** Dependencias necesarias para que el proyecto funcione en tu máquina.

### Backend
- **Java 17+**
- **Maven 3.8+**
- **PostgreSQL** (para persistencia)
- Credenciales de Puntored (para login, proveedores y compra)

### Frontend
- **Node.js 18+**
- npm o yarn

---

## ⚙️ Instalación y ejecución
> **Nivel:0** API básica con Spring Boot y servicios de Puntored
> Instrucciones paso a paso para levantar el proyecto.

### 1️⃣ Backend – Spring Boot
```bash
# Clonar repositorio
git clone https://github.com/usuario/proyecto.git
cd proyecto/backend

# Construir y ejecutar
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run

# Ejecutar pruebas unitarias
mvn test

```
La API quedará disponible en:
```
http://localhost:8080
```

**Configuración de base de datos** (`src/main/resources/application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/puntored_db
spring.datasource.username=puntored_user
spring.datasource.password=AY-BR*+-2025
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

---

### 2️⃣ Frontend – React
```bash
cd proyecto/frontend
npm install
npm run dev

# Ejecutar pruebas unitarias
mvn test
```
El frontend quedará disponible en:
```
http://localhost:5173
```

****

---

## 📡 Endpoints disponibles
> **Nivel:** Rutas expuestas por la API y qué hacen.

| Método | URL | Descripción |
|--------|-----|-------------|
| `POST` | `/api/auth/login` | Autenticación de usuario |
| `POST` | `/api/user/registration` | Registro de nuevo usuario |
| `GET`  | `/api/recharge/suppliers` | Listado de proveedores |
| `POST` | `/api/recharge/buy` | Realizar recarga |
| `GET`  | `/api/recharge/history` | Histórico de recargas |

---

## 🧪 Ejemplos de uso
> **Nivel:** Cómo probar la API usando `curl`.

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{
  "email": "testuser@gmail.com",
  "password": "Test@123"
}'
```

### Registro
```bash
curl -X POST http://localhost:8080/api/user/registration -H "Content-Type: application/json" -d '{
  "name": "Test User",
  "document": "123456789",
  "cellphone": "3122578935",
  "password": "Test@123",
  "email": "test@example.com"
}'
```

### Obtener proveedores
```bash
curl -X GET http://localhost:8080/api/recharge/suppliers -H "Authorization: Bearer <token>"
```

### Comprar recarga
```bash
curl -X POST http://localhost:8080/api/recharge/buy -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{
  "phoneNumber": "3001234567",
  "value": 10000,
  "supplierId": "8753"
}'
```

### Consultar histórico
```bash
curl -X GET http://localhost:8080/api/recharge/history -H "Authorization: Bearer <token>"
```

---

## 📊 Funcionalidades del Frontend
> **Nivel:** Qué puede hacer el usuario desde la interfaz web.

- **Login**  de usuario.
- Consulta de **proveedores** disponibles.
- Realización de **recargas**.
- Visualización de **historial de transacciones**.
- Generación de **resumen y ticket** de compra.

---

## ☁️ Despliegue en la nube (Opcional)
> **Nivel:** Opciones de publicación del sistema en servidores en línea.

Sugerencias:
- **Backend**: AWS Elastic Beanstalk, Azure App Service, GCP App Engine.
- **Frontend**: Netlify, Vercel o AWS Amplify.

---
