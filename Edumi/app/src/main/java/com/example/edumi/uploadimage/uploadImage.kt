import android.content.Context
import android.net.Uri
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun uploadImage(
    context: Context,
    uri: Uri,
    onSuccess: (String) -> Unit,
    onFailure: (String) -> Unit
) {
    val client = OkHttpClient()
    val privateKey = "private_e1t+AYHMO9DNsXgeyL5RqfwyZjs="

    val file = uriToFile(context, uri)
    if (file == null) {
        onFailure("Falha: não foi possível converter Uri para File")
        return
    }

    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
            "file",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        .addFormDataPart("fileName", file.name)
        .build()

    val auth = "Basic ${android.util.Base64.encodeToString(
        "$privateKey:".toByteArray(),
        android.util.Base64.NO_WRAP
    )}"

    val request = Request.Builder()
        .url("https://upload.imagekit.io/api/v1/files/upload")
        .header("Authorization", auth)
        .post(requestBody)
        .build()

    Thread {
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()
                val json = JSONObject(body ?: "")
                val imageUrl = json.getString("url")
                onSuccess(imageUrl)
            } else {
                onFailure("Erro: ${response.code}")
            }
        } catch (e: Exception) {
            onFailure("Falha: ${e.message}")
        } finally {
            file.delete()
        }
    }.start()
}


fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile("upload", ".jpg", context.cacheDir)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
