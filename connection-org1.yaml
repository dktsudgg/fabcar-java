---
name: test-network-org1
version: 1.0.0
client:
  organization: Org1
  connection:
    timeout:
      peer:
        endorser: '300'
organizations:
  Org1:
    mspid: Org1MSP
    peers:
    - peer0.org1.example.com
    certificateAuthorities:
    - ca.org1.example.com
peers:
  peer0.org1.example.com:
    url: grpcs://192.168.0.140:7051
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICJzCCAc2gAwIBAgIUaLnUIQUBvDECOmO9S2wBJqVPxyAwCgYIKoZIzj0EAwIw
        cDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQH
        EwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2Nh
        Lm9yZzEuZXhhbXBsZS5jb20wHhcNMjAwNDI5MDYwNTAwWhcNMzUwNDI2MDYwNTAw
        WjBwMQswCQYDVQQGEwJVUzEXMBUGA1UECBMOTm9ydGggQ2Fyb2xpbmExDzANBgNV
        BAcTBkR1cmhhbTEZMBcGA1UEChMQb3JnMS5leGFtcGxlLmNvbTEcMBoGA1UEAxMT
        Y2Eub3JnMS5leGFtcGxlLmNvbTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABKna
        XuIadKZZ3+aZtx0jHUsOmJih448JRi34vGVMtOW6PS1IqT/v9TQX5o/gjdqhEuA4
        I6IbzbpUZUN8FVBeFrSjRTBDMA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAG
        AQH/AgEBMB0GA1UdDgQWBBS3ydYuE6Pc3L7lq+whwxbXOz/vETAKBggqhkjOPQQD
        AgNIADBFAiEAzsG/fxc2v3bs5rOM/gELMkD5L7WFjJj44L/HILXI8PECIAz3ci63
        b52kPGmcIlqGKjCDJ4Zi5KMkIGBRfjGk9H7s
        -----END CERTIFICATE-----
        
    grpcOptions:
      ssl-target-name-override: peer0.org1.example.com
      hostnameOverride: peer0.org1.example.com
certificateAuthorities:
  ca.org1.example.com:
    url: https://localhost:7054
    caName: ca-org1
    tlsCACerts:
      pem: |
        -----BEGIN CERTIFICATE-----
        MIICJzCCAc2gAwIBAgIUaLnUIQUBvDECOmO9S2wBJqVPxyAwCgYIKoZIzj0EAwIw
        cDELMAkGA1UEBhMCVVMxFzAVBgNVBAgTDk5vcnRoIENhcm9saW5hMQ8wDQYDVQQH
        EwZEdXJoYW0xGTAXBgNVBAoTEG9yZzEuZXhhbXBsZS5jb20xHDAaBgNVBAMTE2Nh
        Lm9yZzEuZXhhbXBsZS5jb20wHhcNMjAwNDI5MDYwNTAwWhcNMzUwNDI2MDYwNTAw
        WjBwMQswCQYDVQQGEwJVUzEXMBUGA1UECBMOTm9ydGggQ2Fyb2xpbmExDzANBgNV
        BAcTBkR1cmhhbTEZMBcGA1UEChMQb3JnMS5leGFtcGxlLmNvbTEcMBoGA1UEAxMT
        Y2Eub3JnMS5leGFtcGxlLmNvbTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABKna
        XuIadKZZ3+aZtx0jHUsOmJih448JRi34vGVMtOW6PS1IqT/v9TQX5o/gjdqhEuA4
        I6IbzbpUZUN8FVBeFrSjRTBDMA4GA1UdDwEB/wQEAwIBBjASBgNVHRMBAf8ECDAG
        AQH/AgEBMB0GA1UdDgQWBBS3ydYuE6Pc3L7lq+whwxbXOz/vETAKBggqhkjOPQQD
        AgNIADBFAiEAzsG/fxc2v3bs5rOM/gELMkD5L7WFjJj44L/HILXI8PECIAz3ci63
        b52kPGmcIlqGKjCDJ4Zi5KMkIGBRfjGk9H7s
        -----END CERTIFICATE-----
        
    httpOptions:
      verify: false
