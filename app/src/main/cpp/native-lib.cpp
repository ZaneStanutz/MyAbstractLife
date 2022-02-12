
#include <jni.h>
#include <string.h>
#include <time.h>
#include <bitset>
#include <cmath>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_trioszs_myabstractlife_MainActivity_resultFromJNI(
        JNIEnv* env,
        jobject /* this */,
        double yearBorn, double monthBorn, double dayBorn) {

    std::string result = "";

    const jdouble beginningOfCtime = 1900.0;  // get number of years since 1900
    const jdouble monthBornAdjustment = 1.00; // month ranges from 0-11
    jdouble yearBornC = 0.00;
    jdouble monthBornC = 0.00;

    yearBornC = yearBorn - beginningOfCtime;
    monthBornC = monthBorn - monthBornAdjustment;

    time_t timer;
    struct tm birthday = {0}; //init empty struct
    double seconds;
    double minutes;
    double hours;

    string secondsString;
    string minutesString;
    string hoursString;

    //define struct
    birthday.tm_hour = 0;
    birthday.tm_year = yearBornC;
    birthday.tm_min = 0;
    birthday.tm_mon = monthBornC;
    birthday.tm_sec = 0;
    birthday.tm_mday = dayBorn;

    //get current time
    time(&timer);

    //get seconds since birthday, round values for readability
    seconds = round(difftime(timer, mktime(&birthday)));
    minutes = round(seconds/60);
    hours = round(minutes/60);

    secondsString = to_string(seconds);
    minutesString = to_string(minutes);
    hoursString = to_string(hours);

    result.append(secondsString);
    result.append(".");
    result.append(minutesString);
    result.append(".");
    result.append(hoursString);

    return env->NewStringUTF(result.c_str());

} // resultfrom JNI()