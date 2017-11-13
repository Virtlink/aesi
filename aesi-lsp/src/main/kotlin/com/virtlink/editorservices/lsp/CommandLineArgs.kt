package com.virtlink.editorservices.lsp

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.DefaultHelpFormatter
import com.xenomachina.argparser.default

class CommandLineArgs(args: Array<out String>) {

    private val parser: ArgParser = ArgParser(args, helpFormatter = DefaultHelpFormatter(
            "Language server.",
            null
    ))

    val trace by parser.flagging("-t", "--trace",
            help = "enable trace of messages")
//            .default(false)

//    val verbose by parser.flagging("-v", "--verbose",
//            help = "enable verbose mode")

    val port by parser.storing("-p", "--port",
            help = "port at which to listen") { toInt() }
            .default(null)//5007)
}