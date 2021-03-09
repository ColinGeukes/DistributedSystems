package sgrub.contracts

import scalaj.http.{Http, HttpOptions, HttpResponse}
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

class EthereumClient {
  val getVersion = """{"jsonrpc":"2.0","method":"web3_clientVersion","params":[],"id":1}"""
  println("client version: ")
  println(RPC(getVersion, verbose = false))

  val getNetId = """{"jsonrpc":"2.0","method":"net_version","params":[],"id":67}"""
  val numAccounts = """{"jsonrpc":"2.0","method":"eth_accounts","params":[],"id":1}"""
  println("account(s): ")
  println(RPC(numAccounts))

  val accountBalance = """{"jsonrpc":"2.0","method":"eth_getBalance","params":["0xf086ac6f286e2ef46f47553362ebd796b0ac788f", "latest"],"id":1}"""
  println("balance for account: ")
  println(RPC(accountBalance))

  /**
   * Execute remote procedure call using JSON RPC api, see https://eth.wiki/json-rpc/API
   * @param data JSON payload
   * @param verbose boolean, set to true for entire JSON response, false for only result
   * @param host geth server address
   * @return String of the response object
   */
  def RPC(data: String, verbose: Boolean = true, host: String = "http://localhost:8545"): String = {
    //send request
    val body = Http(host).postData(data)
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString.body

    //parse result, usage circe see: https://circe.github.io/circe/cursors.html
    val json = parse(body).getOrElse(Json.Null)
    if(verbose) json.toString
    else json.hcursor.downField("result").as[String].merge.toString
  }

}
