# 🛍️ Weshopify – Retail E‑Commerce Platform

**Weshopify** is a microservices-based retail e‑commerce MVP featuring user and admin dashboards, product/category/brand management, and secure order processing. It combines modern frontend and backend stacks to offer a scalable and production-ready architecture.

---

## 🚀 Tech Stack

### Frontend
- ReactJS

### Backend
- Java + Spring Boot (microservices)
- MySQL & MongoDB
- AWS S3 (file uploads)
- WSO2 IAM (authentication & authorization)
- Docker

### Observability & Monitoring
- ELK Stack (Elasticsearch, Logstash, Kibana)
- Prometheus & Grafana

---

## 🔑 Key Features

- **Role-Based UX**: Separate dashboards for customers and admins
- **Product Management**: Full CRUD for categories, brands, and products, with image uploads to AWS S3
- **Order Workflow**: REST APIs to create, manage, and filter orders
- **Filters & Search**: Advanced querying (e.g., by category, brand, price range)
- **Security**: JWT authentication via WSO2 IAM for secure access control
- **Logging & Monitoring**: Centralized logs (ELK) and metrics dashboards (Prometheus & Grafana)
- **Containerized Microservices**: Docker-ready services for simplified dev and deployment environments

---

## 📂 Project Structure

/frontend ← React customer/admin dashboards
/backend ← Spring Boot microservices
├── user ← User & auth management
├── product ← Category/brand/product APIs + AWS S3
├── order ← Order placement & querying
└── config ← Common configs (WSO2, DB, logging)

/infrastructure ← Docker compose or deployment scripts
/monitoring ← ELK, Prometheus & Grafana setup files


---

## 🛠️ Installation & Setup

### Prerequisites
- Java 17+, Node.js 16+, Docker & Docker Compose
- MySQL & MongoDB databases
- AWS account with S3 access
- WSO2 IAM server (or similar IAM solution)

### Steps

```bash
# 1. Clone the repo
git clone https://github.com/Bunnygit7/weshopify.git
cd weshopify

# 2. Configure databases and IAM in each microservice's application.properties
# 3. (Optional) Add AWS S3 credentials
# 4. Start services with Docker Compose
docker-compose up -d

# 5. Frontend setup
cd frontend
npm install && npm start
Visit http://localhost:3000 (frontend) and http://localhost:8080/... (backend APIs)

📈 Monitoring & Logging
Once services are running:

Kibana: http://localhost:5601 (view logs)

Prometheus: http://localhost:9090 (metrics)

Grafana: http://localhost:3001 (dashboards)

🔮 Roadmap
📍 User-friendly UX enhancements (product reviews, wishlists)

📧 SMS/email notifications for orders

🧪 End-to-end testing & CI/CD pipeline

☁️ Deployment on Kubernetes or AWS ECS

👨‍💻 Author
Brought to you by Bunny Bathula
🔗 LinkedIn  |  🌐 Portfolio

📜 License
Distributed under the MIT License. Contribute and enjoy! 🚀
