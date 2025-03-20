# 🎌 **Kokoro List Backend**

A **Spring Boot API** that powers the **[Kokoro List](https://github.com/AbhranilNXT/Kokoro-List)**—an anime watchlist application. This backend integrates with:
- ✅ **Firebase Authentication** for user authentication.
- ✅ **MySQL Database** to store user data, watchlists, and anime information.
- ✅ **Hibernate ORM** for object-relational mapping.
- ✅ **Spring Data JPA** for streamlined database access.

---

## 🚀 **Features**
- 🎉 Add anime to your watchlist, auto-syncing data from Jikan API.
- 🔎 View and manage your watchlist (plan-to-watch, currently watching, and finished).
- ⭐️ Update personal watchlist metrics like rating, notes, and status.
- 📊 View user statistics (watching, completed anime count).
- ❌ Delete user account (self or by admin).
- 🛡️ Admin role management for extended privileges.

---

## 🛠️ **Tech Stack**
- **Backend:** Java 21, Spring Boot 3.4.3
- **Authentication:** Firebase Authentication
- **Database:** MySQL 8.4
- **Build Tool:** Maven

---

## 📚 **Database Schema**

### 🎮 **Anime Table**
```sql
CREATE TABLE anime (
    mal_id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    image_url TEXT,
    synopsis TEXT,
    studio VARCHAR(100),
    year VARCHAR(10),
    genres TEXT,
    episodes VARCHAR(10),
    malscore DOUBLE,
    status VARCHAR(50)
);
```

### 📚 **Watchlist Table**
```sql
CREATE TABLE watchlist (
    watchlist_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36),
    mal_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (mal_id) REFERENCES anime(mal_id)
);
```

### 📊 **UserAnimeMetrics Table**
```sql
CREATE TABLE user_anime_metrics (
    metrics_id VARCHAR(36) PRIMARY KEY,
    watchlist_id VARCHAR(36) UNIQUE,
    started_watching DATETIME,
    finished_watching DATETIME,
    personal_rating INT,
    notes TEXT,
    FOREIGN KEY (watchlist_id) REFERENCES watchlist(watchlist_id)
);
```

### 👤 **Users Table**
```sql
CREATE TABLE users (
    user_id VARCHAR(36) PRIMARY KEY,
    display_name VARCHAR(255),
    avatar_url TEXT,
    bio TEXT,
    is_admin BOOLEAN NOT NULL DEFAULT false
);
```

---

## ⚡️ **Setup and Installation**

### 📥 **1. Clone the Repository**
```bash
git clone https://github.com/your-username/kokoro-list-backend.git
cd kokoro-list-backend
```

### 📝 **2. Configure `application.properties`**
Update `src/main/resources/application.properties` with:

```properties
# Firebase Configuration
firebase.admin.sdk=classpath:service-account.json

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/kokoro_list_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

✅ **Ensure `service-account.json` is placed under `src/main/resources/`.**

---

### 🔥 **3. Build and Run**
```bash
# Build the project
mvn clean package

# Run the application
java -jar target/kokoro-list-backend.jar
```

---

## 🔐 **Firebase Configuration**

### 📝 **Step 1: Download Service Account JSON**
1. Go to [Firebase Console](https://console.firebase.google.com/).
2. Select your project > `Project Settings`.
3. Navigate to the `Service Accounts` tab.
4. Click on `Generate new private key` and download the JSON file.
5. Place the file in `src/main/resources/service-account.json`.

### 📢 **Step 2: Add Firebase to Your Application**
In your `application.properties`:
```properties
firebase.admin.sdk=classpath:service-account.json
```

---

## 🔥 **API Endpoints**

### 📝 **User Authentication and Management**
| Method  | Endpoint                       | Description                     |
|---------|--------------------------------|---------------------------------|
| `POST`  | `/api/auth/login`              | User login or signup          |
| `POST`  | `/api/anime/add`               | Add anime to watchlist          |
| `GET`   | `/api/anime/anime/{id}`        | Get anime by ID          |
| `GET`   | `/api/anime/watchlist/items`   | Get user's watchlist            |
| `GET`   | `/api/anime/watchlist/items/{id}`    | Get watchlist item details      |
| `PATCH` | `/api/anime/watchlist/update/{id}` | Update watchlist metrics      |
| `GET`   | `/api/anime/stats`             | Get user stats                  |
| `DELETE`| `/api/auth/delete/{id}`       | Delete user by ID               |

---

## 📊 **API Usage Examples**

### 1. **Add Anime to Watchlist**
```bash
POST /api/anime/add
Authorization: Bearer <FIREBASE_TOKEN>
Content-Type: application/json

{
  "malId": 123,
  "title": "My Hero Academia",
  "imageUrl": "https://cdn.myanimelist.net/images/anime/12345.jpg",
  "synopsis": "A story about heroes...",
  "studio": "Bones",
  "year": "2016",
  "genres": "Action, Adventure",
  "episodes": "25",
  "malScore": 8.1,
  "status": "Finished Airing"
}
```

---

## 📊 **User Role Management**

### 1. **Grant Admin Privileges (Manual DB Update)**
```sql
UPDATE users
SET is_admin = 1
WHERE user_id = 'user_firebase_id';
```

✅ **Admins can delete any user, while regular users can delete only themselves.**

---

## 📦 **Project Structure**

```
kokoro-list-backend/
├── src/
│   ├── main/
│   │   ├── java/tech/abhranilnxt/kokorolistbackend/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── KokoroListBackendApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── service-account.json
└── pom.xml
```

---

## 🛡️ **Security**
- Firebase Authentication handles user validation.
- JWT Tokens with Bearer scheme for API requests.

---

## 🤝 **Contributing**
1. Fork the repo
2. Create a feature branch (`git checkout -b feature/awesome-feature`)
3. Commit changes (`git commit -m 'Add awesome feature'`)
4. Push to branch (`git push origin feature/awesome-feature`)
5. Open a PR

---

## 📧 **Contact**
For any queries or support, feel free to reach out:
- 📧 Email: [abhranilnxt@gmail.com](mailto:abhranilnxt@gmail.com)
- 🌐 GitHub: [abhranilnxt](https://github.com/AbhranilNXT)
