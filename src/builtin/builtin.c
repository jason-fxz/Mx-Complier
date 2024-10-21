#define bool _Bool
typedef unsigned int size_t; // 32 bits


// C library functions
int printf(const char *format, ...);
int scanf(const char *format, ...);
int sprintf(char *str, const char *format, ...);
void *malloc(size_t size); // NOLINT

typedef __builtin_va_list va_list;
#define va_start(ap, param) __builtin_va_start(ap, param)
#define va_end(ap)          __builtin_va_end(ap)
#define va_arg(ap, type)    __builtin_va_arg(ap, type)

// Builtin: void print(string str);
void print(char *s) { printf("%s", s); }

// Builtin: void println(string str);
void println(char *s) { printf("%s\n", s); }

// Builtin: void printInt(int n);
void printInt(int n) { printf("%d", n); }

// Builtin: void printlnInt(int n);
void printlnInt(int n) { printf("%d\n", n); }

// Builtin: string getString();
char *getString() {
  char *s = (char *)malloc(sizeof(char) * 1024);
  scanf("%s", s);
  return s;
}

// Builtin: int getInt();
int getInt() {
  int n;
  scanf("%d", &n);
  return n;
}

// Builtin: string toString(int i);
char *toString(int i) {
  char *s;
  s = (char *)malloc(sizeof(char) * 12);
  sprintf(s, "%d", i);
  return s;
}

// My Builtin: __mx_allocate(int size)
void *__mx_allocate(int size) {
  return malloc(size);
}

// My Builtin: __mx_allocate_array(int length)
void *__mx_allocate_array(int length) {
  int *a = (int*) malloc(length * 4 + 4);
  a[0] = length;
  return a + 1;
}

// My Builtin: __mx_array_size(void *array)
int __mx_array_size(void *array) { return ((int *)array)[-1]; }

// My Builtin: __mx_bool_to_string(bool b)
char *__mx_bool_to_string(bool b) {
  return b ? "true" : "false";
}

/*
void *__alloca_helper(int size, int length) {
  int *a = (int *)malloc(size * length + 4);
  a[0] = length;
  return a + 1;
}

void *__array_alloca_inside(int size, int depth, int *lengths, int remaining) {
  if (depth == 1) {
    return __alloca_helper(size, *lengths);
  }
  if (remaining == 1) {
    return __alloca_helper(sizeof(void *), *lengths);
  }
  void *array = __alloca_helper(sizeof(void *), *lengths);
  for (int i = 0; i < *lengths; i++) {
    ((void **)array)[i] =
        __array_alloca_inside(size, depth - 1, lengths + 1, remaining - 1);
  }
  return array;
}

void *__array_alloca(int size, int depth, int length, ...) {
  va_list ap;
  int *a = (int *)malloc(sizeof(int) * length);
  va_start(ap, length);
  for (int i = 0; i < length; i++) {
    a[i] = va_arg(ap, int);
  }
  va_end(ap);
  return __array_alloca_inside(size, depth, a, length);
}

int __builtin_array_size(void *array) { return ((int *)array)[-1]; }
*/




// The following 4 functions are string methods,
// turn string_length to string.length in llvm IR

// Builtin: int string.length()
int string_length(char *s) {
  int i = 0;
  while (s[i] != '\0') {
    i++;
  }
  return i;
}

// Builtin string string.substring(int left, int right)
char *string_substring(char *s, int left, int right) {
  int len = right - left;
  char *result = (char *)malloc(sizeof(char) * (len + 1));
  for (int i = 0; i < len; i++) {
    result[i] = s[left + i];
  }
  result[len] = '\0';
  return result; 
}

// Builtin: int string.parseInt()
int string_parseInt(char *s) {
  int result = 0, fg = 0;
  if (*s == '-') fg = 1;
  while (*s != '\0') {
    result = result * 10 + (*s) - '0';
    s++;
  }
  return fg ? -result : result;
}

// Builtin: int string.ord(int i)
int string_ord(char *s, int i) { return s[i]; }


// My Builtin:
// For string comparison
int __mx_string_compare(char *s1, char *s2) {
  int i = 0;
  while (s1[i] != '\0' && s2[i] != '\0') {
    if (s1[i] != s2[i]) {
      return s1[i] - s2[i];
    }
    i++;
  }
  return s1[i] - s2[i];
}

// My Builtin:
// For string concatenation
char *__mx_string_concat(char *s1, char *s2) {
  int len1 = string_length(s1);
  int len2 = string_length(s2);
  char *result = (char *)malloc(sizeof(char) * (len1 + len2 + 1));
  for (int i = 0; i < len1; i++) {
    result[i] = s1[i];
  }
  for (int i = 0; i < len2; i++) {
    result[len1 + i] = s2[i];
  }
  result[len1 + len2] = '\0';
  return result;
}



// void __string_copy(char **s1, char *s2) {
//   int len = __string_length(s2);
//   *s1 = (char *)malloc(sizeof(char) * (len + 1));
//   for (int i = 0; i < len; i++) {
//     (*s1)[i] = s2[i];
//   }
//   (*s1)[len] = '\0';
// }
