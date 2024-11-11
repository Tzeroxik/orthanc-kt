package com.rpgportugal.orthanc.kt.error

interface SchedulerError : DomainError {
    data class FailedToUnschedule(
        val jobName: String,
        override val message: String = "Failed to unshedule job $jobName"
    ) : SchedulerError

    data class FailedToSchedule(
        val jobName: String,
        override val message: String = "Failed to unshedule job $jobName"
    ) : SchedulerError
}