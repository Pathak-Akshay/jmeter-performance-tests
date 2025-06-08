import groovy.json.JsonSlurper

def response = prev.getResponseDataAsString()
def json = new JsonSlurper().parseText(response)

vars.put("token", json.token.toString())
log.info("Token : " + vars.get("token"))