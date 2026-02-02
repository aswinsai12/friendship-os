# 🎉 Friendship OS — Birthday Memory Web App

A full-stack web application where friends can upload birthday wishes, messages, and photos, and the birthday person can view them through an animated celebration experience.

Built as a production-ready cloud application with secure storage, database persistence, and image hosting.

## 🚀 Live Features
### 🎂 Birthday Entrance Page

Confetti animation

Typewriter birthday message

Interactive cake (fireworks on click)

Auto slideshow of uploaded memories

📸 Memory Gallery

Displays all uploaded memories

Responsive card layout

Optimized cloud images

## 🎛 Admin Dashboard

Add memory (name + message + image)

View all memories

Delete memories

## 🧠 System Architecture

Frontend (HTML/CSS/JS)
⬇
REST API Backend
⬇
Cloud Storage + Database

Layer	Technology
Backend	Spring Boot
Database	PostgreSQL (Cloud Hosted)
Image Storage	Cloudinary
Hosting	Render
Containerization	Docker
Language	Java

## 🔐 Security (Production Standard)

### All secrets are stored as environment variables, not in code.

DB_URL
DB_USER
DB_PASS
CLOUD_NAME
CLOUD_KEY
CLOUD_SECRET


This prevents API key leaks and follows industry best practices.

## 📂 Project Structure
friendship-os
 ┣ src/main/java/com/friendshipos
 ┃ ┣ controller
 ┃ ┣ model
 ┃ ┣ repo
 ┃ ┗ FriendshipOsApplication.java
 ┣ src/main/resources
 ┃ ┣ static (HTML pages)
 ┃ ┗ application.properties
 ┣ Dockerfile
 ┣ pom.xml
 ┗ README.md

## ⚙️ How It Works

User uploads memory → Image sent to Cloudinary

Cloudinary returns secure image URL

URL + message saved in PostgreSQL

Gallery & slideshow fetch from database

No images stored on server → fully cloud based.

## 🐳 Deployment

This project is containerized using Docker.

Build:

mvn clean package
docker build -t friendship-os .

Deploy to Render with environment variables set.

## 📈 Learning Value

This project demonstrates:

REST API design

Cloud image hosting

Database integration

Secure environment configuration

Frontend animations

Full deployment pipeline


## 👤 Author

Aswin Varma
Competative Programmer

## 💡 Future Improvements

User authentication

Comment reactions

Video memories

Music background

Shareable memory links

Made with ❤️ for celebrating friendship
