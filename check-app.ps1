Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Journal Application Status Check" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if port 8081 is in use
Write-Host "1. Checking if port 8081 is listening..." -ForegroundColor Yellow
$portCheck = netstat -ano | findstr :8081
if ($portCheck) {
    Write-Host "   ✓ Port 8081 is IN USE" -ForegroundColor Green
    Write-Host "   $portCheck" -ForegroundColor Gray
} else {
    Write-Host "   ✗ Port 8081 is NOT in use (application not running)" -ForegroundColor Red
}
Write-Host ""

# Check Java processes
Write-Host "2. Checking Java processes..." -ForegroundColor Yellow
$javaProcesses = Get-Process java -ErrorAction SilentlyContinue
if ($javaProcesses) {
    Write-Host "   ✓ Java processes found:" -ForegroundColor Green
    $javaProcesses | Format-Table Id, ProcessName, StartTime -AutoSize
} else {
    Write-Host "   ✗ No Java processes running" -ForegroundColor Red
}
Write-Host ""

# Check if JAR exists
Write-Host "3. Checking JAR file..." -ForegroundColor Yellow
if (Test-Path "target\journalApplication-0.0.1-SNAPSHOT.jar") {
    $jarSize = (Get-Item "target\journalApplication-0.0.1-SNAPSHOT.jar").Length / 1MB
    Write-Host "   ✓ JAR file exists ($([math]::Round($jarSize, 2)) MB)" -ForegroundColor Green
} else {
    Write-Host "   ✗ JAR file NOT found" -ForegroundColor Red
}
Write-Host ""

# Test HTTP connection
Write-Host "4. Testing HTTP connection to localhost:8081..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081" -TimeoutSec 3 -ErrorAction Stop
    Write-Host "   ✓ Application is responding!" -ForegroundColor Green
    Write-Host "   Status Code: $($response.StatusCode)" -ForegroundColor Gray
} catch {
    Write-Host "   ✗ Cannot connect to application" -ForegroundColor Red
    Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Gray
}
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "To start the application, run:" -ForegroundColor Yellow
Write-Host "  .\mvnw.cmd spring-boot:run" -ForegroundColor White
Write-Host "========================================" -ForegroundColor Cyan

