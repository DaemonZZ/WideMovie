
### **WideMovie MultiPlatform App**

**Nền tảng hỗ trợ:**
- ✅ Android
- ✅ iOS
- ✅ WebAssembly

---

### **Những thư viện cần thay thế để chạy KMP (Kotlin MultiPlatform):**
| Android Library | Kotlin Multiplatform Thay thế |
|------------------|-------------------------------|
| Hilt             | [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/) |
| Navigation       | [Voyager Navigation](https://voyager.adriel.cafe/navigation/) |
| Retrofit         | [Ktor Client](https://ktor.io/docs/client-create-multiplatform-application.html) |
| Room Database    | [SQLDelight](https://sqldelight.github.io/sqldelight/2.0.2/multiplatform_sqlite/) |

---

### **Những hạn chế:**
- ❌ Không sử dụng được **Kapt** (chỉ hỗ trợ Android).
- ⚠️ Hạn chế sử dụng `Dispatchers.IO` (chỉ dùng được trong `androidMain`).
- 🚫 Không thể dùng **XML resource**  
  👉 Dùng [`compose.resource`](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources.html) thay thế.
- 🧩 Không thể sử dụng **Material3** cho `wasmJs`  
  👉 Cần chuyển sang **Material2** hoặc xử lý riêng cho `wasmJs`.
- ⚠️ **Koin Navigation** không tương thích hoàn toàn.
- 🐞 **Ktor** bản mới nhất lỗi khi build trên **iOS Emulator**.
- 🚫 **SQLDelight** không hỗ trợ Wasm, cần phương án thay thế nếu cần thiết

---

### **Những điểm cần lưu ý:**
- 🔹 Sử dụng **Kotlin 2.0**  
  👉 Xem thêm về `expect`/`actual` và `typealias`:  
  [Kotlin Multiplatform - Expect/Actual](https://kotlinlang.org/docs/multiplatform-expect-actual.html)
- 🔹 Làm việc với API qua [Ktor Client](https://ktor.io/docs/client-create-multiplatform-application.html).
- 🔹 Cách triển khai **UI chung và UI riêng theo platform**.
- 🔹 Xử lý resource và navigation:
  - [Voyager Navigation Docs](https://voyager.adriel.cafe/navigation/)
  - [Koin MP Docs](https://insert-koin.io/docs/reference/koin-mp/kmp/)
  - [SQLDelight MP Docs](https://sqldelight.github.io/sqldelight/2.0.2/multiplatform_sqlite/)
