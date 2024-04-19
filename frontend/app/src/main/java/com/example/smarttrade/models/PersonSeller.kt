package com.example.smarttrade.models

object PersonSeller {
    private var name:String = ""
    private var surnames:String = ""
    private var email:String = ""
    private var userID:String = ""
    private var password:String = ""
    private var cif:String = ""
    private var iban:String = ""
    fun setName(name:String){
        this.name = name
    }

    fun setSurname(surname:String){
        surnames = surname
    }

    fun setEmail(email:String){
        this.email = email
    }

    fun setUserId(uID:String) {
        userID = uID
    }

    fun setPassword(pass:String) {
        password = pass
    }
    fun getName(): String {
        return name
    }

    fun getSurname(): String {
        return surnames
    }

    fun getEmail(): String {
        return email
    }

    fun getUserId(): String {
        return userID
    }

    fun getPassword(): String {
        return password
    }
    fun setCIF(CIF:String) {
        cif = CIF
    }
    fun getCIF(): String {
        return cif
    }
    fun setIBAN(IBAN:String) {
        iban = IBAN
    }
    fun getIBAN(): String {
        return iban
    }

}