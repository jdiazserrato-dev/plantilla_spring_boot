### Task 10: Database Scripts and Docker Compose

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\schema.sql`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\data.sql`
- Create: `C:\Users\jorel\Desktop\template-api\docker-compose.yml`

**Interfaces:**
- Consumes: MySQL database requirements
- Produces: SQL scripts, Docker Compose configuration

- [ ] **Step 1: Create schema.sql**

```sql
CREATE TABLE IF NOT EXISTS health_check (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

- [ ] **Step 2: Create data.sql**

```sql
INSERT INTO health_check (status) VALUES ('OK') ON DUPLICATE KEY UPDATE status = 'OK';
```

- [ ] **Step 3: Create docker-compose.yml**

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: template-api-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: test
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

- [ ] **Step 4: Commit**

```bash
git add src/main/resources/schema.sql
git add src/main/resources/data.sql
git add docker-compose.yml
git commit -m "feat: add database scripts and Docker Compose for MySQL"
```
