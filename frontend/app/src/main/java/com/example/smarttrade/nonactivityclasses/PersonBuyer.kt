package com.example.smarttrade.nonactivityclasses

object PersonBuyer {
    lateinit private var name:String
    lateinit private var surnames:String
    lateinit private var email:String
    lateinit private var userID:String
    lateinit private var password:String
    lateinit private var shippingAddresses:MutableList<String>
    lateinit private var DNI:String
    lateinit private var factAddresses:MutableList<String>
    lateinit private var bizum:String
    lateinit private var paypal:String
    lateinit private var creditCards:MutableList<CreditCard>



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

    fun addShippingAddress(address:String) {
        shippingAddresses.add(address)
    }
    fun getShippingAddresses(): MutableList<String> {
        return shippingAddresses
    }

    fun setDNI(DNI:String) {
        this.DNI = DNI
    }
    fun getDNI(): String {
        return DNI
    }

    fun addFacturacionAddress(address:String) {
        factAddresses.add(address)
    }
    fun getFacturationAddresses(): MutableList<String> {
        return factAddresses
    }

    fun setBizum(Bizum:String) {
        this.bizum = Bizum
    }
    fun getBizum(): String {
        return bizum
    }

    fun setPaypal(Paypal:String) {
        this.paypal = Paypal
    }
    fun getPaypal(): String {
        return paypal
    }
    fun addCreditCard(creditCard: CreditCard) {
        creditCards.add(creditCard)
    }
    fun getCreditCards(): MutableList<CreditCard> {
        return creditCards
    }

}