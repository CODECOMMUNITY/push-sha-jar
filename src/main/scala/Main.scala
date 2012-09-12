import java.io.File

case class Credentials(user: String, pw: String)
case class Arguments(jar: File, uri: String, cred: Credentials)

object Main extends App {

  def usage(err: Option[String] = None): Nothing = {
    err foreach (msg => println("\t" + msg))
    println("""| Usage: java -jar push.jar <file> <remote url> <user> <encrypted pw>
               |
               |  This utility is used to push JAR files to a maven/ivy repository.
               |""".stripMargin)
    throw new Exception()
  }
  
  def parseArgs: Arguments = {
    if(args.length != 4) usage()
    val file = Option(new File(args(0))) filter (_.isFile) getOrElse usage(Some(args(0) + " is not a file!"))
    val uri = Option(args(1)) getOrElse usage(Some("uri is empty"))
    val user = Option(args(2)) getOrElse usage(Some("user is empty"))
    val pw = Option(args(3)) getOrElse usage(Some("password is empty"))
    Arguments(file, uri, Credentials(user, pw))
  }
  
  // Pushes a file and writes the new .desired.sha1 for git.
  def pushFile(file: File, uri: String, cred: Credentials): Unit = {
    val url = uri
    val sender = dispatch.url(uri).PUT.as(cred.user,cred.pw) <<< (file, "application/java-archive")
    // TODO - output to logger.
    dispatch.Http(sender >>> System.out)
  }
  
  
  try {
    val a =  parseArgs
    pushFile(a.jar, a.uri, a.cred)
  } catch {
    case t: Exception => System.exit(1)
  }
}


