package ipvc.estg.ecgm.diogo_araujo.roadalert.api

data class OutputReports(
    val id: Int,
    val username: String,
    val title: String,
    val situation: String,
    val location: String,
    val image: String,
    val date: String
)