# ABIR Android Starter (বাংলা নির্দেশিকা)

এটি ABIR শিক্ষা অ্যাপের **Android Studio starter project**। এতে নীল-সাদা UI, Home, Courses, Exam, Profile এবং Admin-এর নমুনা স্ক্রিন আছে।

## গুরুত্বপূর্ণ সীমাবদ্ধতা
- এটি এখনো সম্পূর্ণ চালু অনলাইন অ্যাপ নয়।
- Google Sign-In বোতামটি আপাতত সেটআপ-সংক্রান্ত বার্তা দেখায়; বাস্তব Google Authentication যুক্ত করা হয়নি।
- Firebase Database, PDF upload, আসল MCQ পরীক্ষা, ফলাফল সংরক্ষণ ও Admin permission এখনো যুক্ত করতে হবে।
- Admin স্ক্রিনটি UI নমুনা; নিরাপদ সার্ভার-সাইড অনুমতি ছাড়া কাউকে Admin করা যাবে না।

## চালানোর নিয়ম
1. কম্পিউটারে Android Studio ইনস্টল করো।
2. Android Studio থেকে `Open` নির্বাচন করে এই ফোল্ডারটি খোলো।
3. Gradle sync শেষ হওয়া পর্যন্ত অপেক্ষা করো।
4. Android ফোনে Developer Options ও USB debugging চালু করে USB দিয়ে যুক্ত করো, অথবা Emulator চালু করো।
5. `Run` চাপো।

## পরের ধাপ: Firebase ও Google Login
1. Firebase Console-এ নতুন project তৈরি করো।
2. Android app package name দাও: `com.abir.education`
3. তোমার অ্যাপের SHA-1/SHA-256 fingerprint Firebase-এ যোগ করো।
4. Google Sign-In provider enable করো।
5. Firebase থেকে `google-services.json` ডাউনলোড করে `app/` ফোল্ডারে রাখো।
6. Firebase Authentication ও Firestore সংযুক্ত করো।
7. Admin email-কে backend-এ অনুমোদিত Admin role দাও এবং Firestore Security Rules দিয়ে সুরক্ষিত করো।

**কখনোই Firebase service-account private key, Google password বা OTP অ্যাপে/চ্যাটে শেয়ার করবে না।**
