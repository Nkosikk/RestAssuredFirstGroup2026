# Pipeline Database Configuration Guide

## 🔒 Setup GitHub Secrets for Database Connection

### Step 1: Add Secrets to Your Repository

1. **Go to GitHub Repository Settings**
   - Navigate to: `Settings` → `Secrets and variables` → `Actions`

2. **Create Three Secrets** (Click "New repository secret")

   | Secret Name | Value | Example |
   |------------|-------|---------|
   | `DB_URL` | Database JDBC URL | `jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching` |
   | `DB_USERNAME` | Database username | `ndosian6b8b7_teaching` |
   | `DB_PASSWORD` | Database password | `^{SF0a=#~[~p)@l1` |

3. **Add Each Secret:**
   - Click "New repository secret"
   - Enter Name: `DB_URL`
   - Enter Value: `jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching`
   - Click "Add secret"
   - Repeat for `DB_USERNAME` and `DB_PASSWORD`

### Step 2: Verify Setup

After adding secrets, you should see all three listed:
```
✓ DB_URL
✓ DB_USERNAME
✓ DB_PASSWORD
```

---

## 📋 How It Works

### Without Secrets (Bad ❌)
```java
String dbURL = "jdbc:mysql://102.222.124.22:3306/...";
String dbUsername = "ndosian6b8b7_teaching";
String dbPassword = "^{SF0a=#~[~p)@l1";  // ← EXPOSED IN CODE!
```

### With Secrets (Good ✅)
```yaml
- name: Run tests
  run: mvn test
  env:
    DB_URL: ${{ secrets.DB_URL }}
    DB_USERNAME: ${{ secrets.DB_USERNAME }}
    DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
```

The code reads from environment variables:
```java
String dbURL = System.getenv("DB_URL");
String dbUsername = System.getenv("DB_USERNAME");
String dbPassword = System.getenv("DB_PASSWORD");
```

---

## 🚀 Pipeline Flow

1. **Build** → Maven compiles project
2. **Run Tests** → TestNG tests execute with DB credentials from secrets
3. **Database Connection** → Code reads `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` from environment
4. **Upload Results** → Test reports saved as artifacts

---

## ✅ Easy to Use

### For Local Development:
No secrets needed - hardcoded defaults work fine

### For Pipeline:
- Secrets are automatically injected as environment variables
- Secure - credentials never appear in logs
- Easy to change - just update secrets in GitHub UI

---

## 🔧 What If Database is Unreachable?

If tests fail with connection errors:

1. **Check Network Access**
   - GitHub Actions runners are in the cloud
   - Your database at `102.222.124.22` must be accessible from the internet
   - Verify firewall/security group allows inbound traffic

2. **Verify Credentials**
   - Make sure `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` are correct
   - Test locally first: `mvn test`

3. **Alternative: Use Mock Database**
   - For pipeline testing, consider using H2 or embedded database
   - Use environment to switch between real DB and mock DB

---

## 📝 Local Development

For local testing without modifying code:

### Option 1: Set Environment Variables
```bash
export DB_URL="jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching"
export DB_USERNAME="ndosian6b8b7_teaching"
export DB_PASSWORD="^{SF0a=#~[~p)@l1"
mvn test
```

### Option 2: Use Maven Properties
```bash
mvn test \
  -DDB_URL="jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching" \
  -DDB_USERNAME="ndosian6b8b7_teaching" \
  -DDB_PASSWORD="^{SF0a=#~[~p)@l1"
```

### Option 3: Use Hardcoded Defaults (Current)
- Code has fallback values
- Runs without any environment variables
- Works for local testing

---

## 🛡️ Security Best Practices

✅ **Do:**
- Store sensitive data in GitHub Secrets
- Use environment variables in code
- Rotate passwords regularly
- Use restrictive database permissions

❌ **Don't:**
- Hardcode credentials in code
- Commit secrets to Git
- Share passwords in issues/comments
- Use same password everywhere

---

## 📊 Summary

| Aspect | Status | Details |
|--------|--------|---------|
| Local Development | ✅ Easy | Hardcoded defaults work |
| Pipeline Execution | ✅ Easy | Use GitHub Secrets |
| Security | ✅ Good | Credentials in secrets, not code |
| Maintenance | ✅ Easy | Change secrets without pushing code |

---

## Next Steps

1. **Add GitHub Secrets** (Settings → Secrets)
2. **Commit updated files:**
   ```bash
   git add .github/workflows/test-pipeline.yml src/test/java/utils/DatabaseConnection.java
   git commit -m "Add secure database configuration for pipeline"
   git push
   ```
3. **Push to main** and watch pipeline run with DB access
4. Check test results in GitHub Actions

**Done!** 🎉 Your pipeline can now easily connect to the database.

