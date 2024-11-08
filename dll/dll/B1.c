#include <jni.h>
#include <stdio.h>
#include "B1.h"
JNIEXPORT int JNICALL Java_B1_add(JNIEnv *env, jobject obj, jint a, jint b)
{
    printf("\n%d + %d = %d\n", a, b, (a + b));
    return;
}

/*Exeution Steps:
$ javac B1.java
$ ls
$ gcc -shared -fPIC -I/usr/lib/jvm/default-java/include -I/usr/lib/jvm/default-java/include/linux B1.c -o libB1.so
$ ls
$ java -classpath . -Djava.library.path=. B1
*/