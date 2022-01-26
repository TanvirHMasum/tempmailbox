package com.example.mailbox.model

//------Available Domain View------//
data class DomainData(
    val id: String,
    val domain: String
)

/*
{
    "@context": "/contexts/Domain",
    "@id": "/domains",
    "@type": "hydra:Collection",
    "hydra:member": [
    {
        "@id": "/domains/61f02efa959f2c1a8a35322a",
        "@type": "Domain",
        "id": "61f02efa959f2c1a8a35322a",
        "domain": "sinaite.net",
        "isActive": true,
        "isPrivate": false,
        "createdAt": "2022-01-25T00:00:00+00:00",
        "updatedAt": "2022-01-25T00:00:00+00:00"
    }
    ],
    "hydra:totalItems": 1
}
*/

data class CrateAccData(
    val address: String,
    val password: String
)

data class CreateAccResponse(
    val id: String,
    val address: String,
    val used: Int
)

data class LoginAccData(
    val address: String,
    val password: String
)

data class LoginDataResponse(
    val token: String,
    val id: String
)


data class AccResponse(
    val id: String,
    val address: String,
    val used: Int
)

data class InboxTextData(
    val address: String,
    val name: String,
)

/*
{
    "@context": "/contexts/Message",
    "@id": "/messages",
    "@type": "hydra:Collection",
    "hydra:member": [
        {
            "@id": "/messages/61f109033a8c219480a5c874",
            "@type": "Message",
            "id": "61f109033a8c219480a5c874",
            "accountId": "/accounts/61eed07a2556a4602c4153e2",
            "msgid": "<CADXfigRDQgurGRV3hiOKQ-Ra7954mnHDe2QHqgs-W-ORCs6-PQ@mail.gmail.com>",
            "from": {
                "address": "mirazkhanbd88@gmail.com",
                "name": "Mirazz Hossain"
            },
            "to": [
                {
                    "address": "tanvirmmm@canfga.org",
                    "name": ""
                }
            ],
            "subject": "Greetings",
            "intro": "Hello, Dear Friend. Hope you are well",
            "seen": false,
            "isDeleted": false,
            "hasAttachments": false,
            "size": 2751,
            "downloadUrl": "/messages/61f109033a8c219480a5c874/download",
            "createdAt": "2022-01-26T08:40:20+00:00",
            "updatedAt": "2022-01-26T08:40:35+00:00"
        }
    ],
    "hydra:totalItems": 1
}
*/
