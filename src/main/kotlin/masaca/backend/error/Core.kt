package masaca.backend.error

import io.ktor.http.*

abstract class MasacaError(message: String) : Throwable(message) {
    abstract val httpStatusCode: HttpStatusCode
    abstract val response: ErrorResponse
}

class MasacaCreationError(message: String) : MasacaError(message) {
    override val httpStatusCode: HttpStatusCode
        get() = HttpStatusCode.InternalServerError
    override val response: ErrorResponse
        get() = ErrorResponse("CreationError", this.message)
}

class MasacaNotFoundError(message: String) : MasacaError(message) {
    override val httpStatusCode: HttpStatusCode
        get() = HttpStatusCode.NotFound
    override val response: ErrorResponse
        get() = ErrorResponse("NotFound", this.message)
}

class MasacaInvalidParameterError(message: String) : MasacaError(message) {
    override val httpStatusCode: HttpStatusCode
        get() = HttpStatusCode.BadRequest
    override val response: ErrorResponse
        get() = ErrorResponse("InvalidParameter", this.message)
}

class MasacaValidationError(message: String) : MasacaError(message) {
    override val httpStatusCode: HttpStatusCode
        get() = HttpStatusCode.BadRequest
    override val response: ErrorResponse
        get() = ErrorResponse("ValidationError", this.message)
}
