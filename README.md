Sample Request to test 

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:cal="http://medium.com/types/calculator">
    <soapenv:Header>
    <wsse:Security soapenv:mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
        <wsse:UsernameToken>
            <wsse:Username>user</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">password</wsse:Password>

        </wsse:UsernameToken>
    </wsse:Security>
</soapenv:Header>
    <soapenv:Body>
        <cal:AdditionInput>
            <cal:number1>2</cal:number1>
            <cal:number2>4</cal:number2>
        </cal:AdditionInput>
    </soapenv:Body>
</soapenv:Envelope>
