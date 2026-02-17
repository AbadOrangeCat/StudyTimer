# StudyTimer

StudyTimer is a small Android app that helps you **track how long you actually study**.

Many people make study plans, but they do not know the real numbers. This app solves that with a simple idea: **press Start when you begin, and save a session when you finish**. Over time, StudyTimer builds a history and shows basic statistics, so your next plan can be based on facts instead of guesses.

**Minimum Android version:** Android 4.0.3 (API 15)

---

## What StudyTimer can do

StudyTimer focuses on the essentials. It is designed to be simple, so you spend less time “setting up an app” and more time studying.

- **One-tap timer**: start, pause, resume, and reset a timer.
- **Save study sessions**: give each session a name (for example: “Math homework”, “Reading”, “Exam prep”).
- **History list**: see every saved session with its **duration**, **name**, and **date**.
- **Daily statistics**:
  - **Today**: how much you studied today.
  - **Average per day**: your average study time across days you have used the app.
- **Focus Mode**: when enabled, the timer **only runs while you stay on the main screen**. If you leave the app, it pauses.

---

## A quick tour of the app

If you are new here, this section explains the app like a short story. You can read it once and then you will know what every screen is for.

### Home screen

This is where you spend most of your time.

- You see the **current timer**.
- You can **Start**, **Pause**, or **Resume** the timer.
- You can **Reset** the timer if you want to start again.
- You also see simple stats, like **today’s total** and **average per day**.

### Saving a session

When you finish studying, you can save what you just did.

- Tap the button to save.
- Type a **session name**.
- The app stores it in your **History** list.

A “session” is simply **one block of time you studied**.

### History screen

This screen is your record.

- It shows all saved sessions.
- It includes the session **name**, **date**, and **duration**.
- You can clear the history.  
  When you clear it, the app also resets the statistics.

### Settings screen

This is where you personalize the app.

- Set the name used in the welcome message.
- Turn **Focus Mode** on or off.

---

## Getting started

There are two ways to use StudyTimer. If you only want to use the app, install it like a normal Android app. If you want to edit the code, build it from source.

### Option A: Install the APK

An **APK** is the install file for Android apps (similar to an `.exe` installer on Windows).

1. Download the APK:
   - The file is stored under the `app/release/app-release.apk`.
2. On your Android phone, open the APK file.
3. If Android blocks the install, enable “Install unknown apps” for your file manager or browser, then try again.

> Note: Android phones can have slightly different menus depending on the brand.

### Option B: Build from source (for developers)

If you want to change the app or learn from the code, this is the normal workflow.

Before you start, here are the key terms in plain English:

- **Android Studio**: the official app-building tool from Google. It is where you open this project.
- **Gradle**: the build tool Android Studio uses to turn code into an APK.

Steps:

1. Install **Android Studio**.
2. Open this project folder in Android Studio.
3. Let Android Studio download the required build tools (Gradle will do this automatically).
4. Click **Run** to install the app on an emulator or a real phone.

If you prefer the command line, you can also build the app with Gradle:

```bash
# From the project root folder
./gradlew assembleDebug
```

The APK will be generated in a build output folder under `app/`.

---

## How your data is stored

StudyTimer saves everything **locally on your phone**.

- It uses Android’s local storage (SharedPreferences) for simple numbers like “today’s total”.
- It stores the session list as JSON (a common text format) using Gson.
- There is **no account system** and no online sync in this project.

---

## Project structure (for readers of the code)

If you are reading the code, these files are the main entry points:

- `MainActivity.java`  
  The Home screen: timer logic, saving sessions, and updating daily stats.
- `History.java`  
  Shows the saved sessions list and can reset history + stats.
- `PreferenceScreen.java` + `PreferenceScreenFragment.java`  
  The Settings screen (welcome name and Focus Mode).
- `savehistory.java`  
  Writes/reads the session list to/from local storage.
- `TimeAdp.java` + `TimerModel.java`  
  The History list adapter and the session data model.

---

## Code protection (obfuscation)

This repository includes a `proguard-rules.pro` file.

**Obfuscation** is a build step that makes compiled code harder to read, which can help protect the app against simple reverse‑engineering. In this project, release obfuscation is currently **disabled by default** (the release build uses `minifyEnabled false`), but the ProGuard rules file is already in place if you want to enable it later.
