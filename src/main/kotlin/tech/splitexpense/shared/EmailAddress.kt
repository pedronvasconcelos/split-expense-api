package tech.splitexpense.shared

class EmailAddress(private val email: String){
    init {
        validate()
    }

    private fun validate() {
        if (!email.contains("@")) {
            throw IllegalArgumentException("Invalid email address")
        }
    }

    override fun toString(): String = email

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as EmailAddress
        return email == other.email
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}