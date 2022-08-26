package qq.nukus.schedulednotificationbuilder.exceptions

/**
 * Created by Rasul 26.08.2022.
 */

data class UninitializedValueException(override val message: String) : Exception(message)