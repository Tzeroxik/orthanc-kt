package com.rpgportugal.orthanc.kt.discord.modules.spamCatcher

import com.rpgportugal.orthanc.kt.logging.Logging
import net.dv8tion.jda.api.JDA
import org.quartz.Job
import org.quartz.JobExecutionContext

class HoneypotWarnJob : Job, Logging {
    override fun execute(context: JobExecutionContext?) {
        val jobDataMap = context?.jobDetail?.jobDataMap
        val jda = (jobDataMap?.get("jda") ?: return) as JDA
        val honeypotChannelId = jobDataMap.getString("honeypotChannelId") ?: return

        val honeypotChannel = jda.getTextChannelById(honeypotChannelId)
        honeypotChannel?.sendMessage("**NÃO ESCREVAM NESTE CANAL** Escrita neste canal resulta num soft ban! Isto é um canal anti-bots, mutem o canal por favor!")?.queue()
    }
}