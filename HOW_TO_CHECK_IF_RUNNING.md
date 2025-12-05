# How to Check if Application is Running & Troubleshoot Issues

## ✅ **Method 1: Check Port 8081**

Open PowerShell and run:
```powershell
netstat -ano | findstr :8081
```

**If running:** You'll see output like:
```
TCP    0.0.0.0:8081           0.0.0.0:0              LISTENING       12345
```

**If NOT running:** No output will appear.

---

## ✅ **Method 2: Test HTTP Connection**

Open PowerShell and run:
```powershell
curl http://localhost:8081
```

**If running:** You'll get a response (even if it's an error page, it means the server is up)

**If NOT running:** You'll get:
```
curl: Failed to connect to localhost port 8081
```

---

## ✅ **Method 3: Check Java Processes**

Open PowerShell and run:
```powershell
Get-Process java -ErrorAction SilentlyContinue
```

**If running:** You'll see Java processes listed

**If NOT running:** No output

---

## 🚀 **How to Start the Application**

### Option 1: Using Maven (Recommended)
```powershell
cd "C:\1.Full Stack Websites\Deployed\journalApplication"
.\mvnw.cmd spring-boot:run
```

### Option 2: Using JAR file
```powershell
cd "C:\1.Full Stack Websites\Deployed\journalApplication"
java -jar target\journalApplication-0.0.1-SNAPSHOT.jar
```

---

## 🔍 **What to Look For When Starting**

### ✅ **SUCCESS Indicators:**
- `Started JournalApplication in X.XXX seconds`
- `Tomcat started on port(s): 8081 (http)`
- `Started JournalApplication`
- No red error messages

### ❌ **ERROR Indicators:**

#### 1. **MongoDB Connection Error**
```
MongoSocketException: Exception sending message
```
**Fix:** Check your MongoDB connection string in `application.properties`

#### 2. **Port Already in Use**
```
WebServerException: Unable to start embedded Tomcat
Address already in use: bind
```
**Fix:** 
- Kill the process using port 8081: `netstat -ano | findstr :8081` then `taskkill /PID <PID> /F`
- Or change port in `application.properties` to 8082

#### 3. **Missing Bean Error**
```
No qualifying bean of type 'X' available
```
**Fix:** Check if the class has `@Component`, `@Service`, or `@Repository` annotation

#### 4. **Java Version Error**
```
UnsupportedClassVersionError
```
**Fix:** Install JDK 17 (required for this project)

#### 5. **Missing Dependencies**
```
ClassNotFoundException
```
**Fix:** Run `.\mvnw.cmd clean install` to download dependencies

---

## 🛠️ **Common Issues & Solutions**

### Issue: Application starts but immediately stops
**Check:** Look for error messages in the console output

### Issue: Cannot connect to MongoDB
**Check:** 
- MongoDB Atlas cluster is running
- IP address is whitelisted in MongoDB Atlas
- Connection string is correct in `application.properties`

### Issue: Email not working
**Check:**
- Gmail App Password is correct (not regular password)
- 2-Step Verification is enabled in Google Account

### Issue: Port conflict
**Solution:** Change port in `application.properties`:
```properties
server.port=8082
```

---

## 📝 **Quick Diagnostic Commands**

Run these in PowerShell to diagnose:

```powershell
# 1. Check if port is in use
netstat -ano | findstr :8081

# 2. Check Java version
java -version

# 3. Check if JAR exists
Test-Path target\journalApplication-0.0.1-SNAPSHOT.jar

# 4. Test HTTP connection
Invoke-WebRequest -Uri "http://localhost:8081" -TimeoutSec 3

# 5. Check Java processes
Get-Process java
```

---

## 🎯 **Step-by-Step: Start and Verify**

1. **Open PowerShell** in the project directory
2. **Run:** `.\mvnw.cmd spring-boot:run`
3. **Wait** for "Started JournalApplication" message
4. **Open browser** and go to: `http://localhost:8081`
5. **Check logs** for any error messages

---

## 📞 **If Still Not Working**

1. **Copy the full error message** from the console
2. **Check the last 50 lines** of output
3. **Verify all prerequisites:**
   - Java 17 installed
   - MongoDB connection working
   - Port 8081 available
   - All dependencies downloaded

