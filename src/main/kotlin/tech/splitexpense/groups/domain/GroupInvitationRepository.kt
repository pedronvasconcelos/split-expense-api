package tech.splitexpense.groups.domain

interface GroupInvitationRepository {
    fun save(groupInvitation: GroupInvitation): GroupInvitation
    fun getById(id: GroupInvitationId): GroupInvitation?
    fun update(groupInvitation: GroupInvitation): GroupInvitation
}