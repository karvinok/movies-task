# Testing the CI/CD Pipeline

## Prerequisites

Before testing, ensure you have configured these GitHub Secrets:

1. Go to your repository on GitHub
2. Navigate to **Settings → Secrets and variables → Actions**
3. Add the following secrets:

### Required Secrets:
- `BASE_URL` - TMDB API base URL (e.g., `https://api.themoviedb.org/3/`)
- `TMDB_API_KEY` - Your TMDB API key
- `TMDB_TOKEN` - Your TMDB access token
- `TELEGRAM_BOT_TOKEN` - Your Telegram bot token
- `CHANNEL_ID` - Your Telegram channel ID (e.g., `-1001234567890`)

## Test Methods

### Method 1: Local Build Test (Environment Variables)

Test that environment variables work locally:

```bash
# Edit the test script with your actual credentials
nano test-env-build.sh

# Run the test
./test-env-build.sh
```

This will:
- Temporarily move your `local.properties`
- Build using environment variables
- Restore your `local.properties`

### Method 2: Push to Branch (Automatic Trigger)

The pipeline automatically runs on pushes to `main` or `claude/**` branches:

```bash
# Make any change (or create an empty commit)
git commit --allow-empty -m "Test pipeline"

# Push to trigger the workflow
git push origin claude/update-cmp-version-RYYpf
```

Watch the workflow at: `https://github.com/YOUR_USERNAME/movies-task/actions`

### Method 3: Manual Trigger (workflow_dispatch)

1. Go to your repository on GitHub
2. Click **Actions** tab
3. Select **Build and Deploy to Telegram** workflow
4. Click **Run workflow** dropdown
5. Select branch: `claude/update-cmp-version-RYYpf`
6. Click **Run workflow** button

### Method 4: Test Telegram Integration Locally

Test sending a file to Telegram without building:

```bash
# Set your credentials
BOT_TOKEN="your_bot_token"
CHANNEL_ID="your_channel_id"

# Create a test file
echo "Test APK" > test.apk

# Send to Telegram
curl -F "chat_id=${CHANNEL_ID}" \
     -F "document=@test.apk" \
     -F "caption=🧪 Test Message from Local" \
     -F "parse_mode=HTML" \
     "https://api.telegram.org/bot${BOT_TOKEN}/sendDocument"

# Clean up
rm test.apk
```

## What to Expect

### Successful Pipeline Run:
1. ✅ Code is checked out
2. ✅ JDK 17 is set up
3. ✅ Gradle builds the release APK
4. ✅ APK is sent to your Telegram channel with:
   - File name
   - Branch name
   - Commit hash
   - Commit message
5. ✅ APK is uploaded as GitHub artifact (available for 30 days)

### In Your Telegram Channel:
You should receive a message like:

```
🎬 Movies App - New Build

📦 File: app-release.apk
🔖 Branch: claude/update-cmp-version-RYYpf
📝 Commit: 9905b64
💬 Message: Support environment variables for TMDB credentials in CI/CD

Built with ❤️ by GitHub Actions
```

## Troubleshooting

### Pipeline Fails at Build Step:
- Check that all 3 TMDB secrets are set correctly
- Verify the secrets don't have extra spaces or quotes

### Telegram Upload Fails:
- Verify `TELEGRAM_BOT_TOKEN` is correct
- Verify `CHANNEL_ID` is correct (should be negative for channels)
- Ensure bot is admin in the channel
- Check bot has permission to send files

### Finding Your Channel ID:
```bash
# Replace YOUR_BOT_TOKEN with your actual token
# First, send a message to your channel
# Then run:
curl https://api.telegram.org/botYOUR_BOT_TOKEN/getUpdates

# Look for "chat":{"id":-1001234567890,...}
```

## Quick Verification

After setting up secrets, verify they're accessible:

1. Go to your repository
2. **Settings → Secrets and variables → Actions**
3. You should see all 5 secrets listed

Note: You can't view secret values after creation, only update them.
