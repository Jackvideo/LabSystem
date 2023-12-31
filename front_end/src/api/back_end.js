import request from '@/utils/request'

export default {
    //ResearchLab
    //按参数获取对象
    getLabList(researchlab) {
        return request({
            url: '/LabSystem/researchlab/list',
            method: 'get',
            params: {
                pageNo: researchlab.pageNo,
                pageSize: researchlab.pageSize,
                labid: researchlab.labid,
                labname: researchlab.labname,
                researcharea: researchlab.researcharea
            }
        });
    },
    //新增对象
    addLab(newLab) {
        return request({
            url: '/LabSystem/researchlab',
            method: 'post',
            data: newLab
        });
    },
    //修改对象
    updateLab(newLab) {
        return request({
            url: '/LabSystem/researchlab/update',
            method: 'put',
            data: newLab
        })
    },
    //逻辑判断是新增还是修改
    saveLab(newLab) {
        if (newLab.labid == null && newLab.labid == undefined) {
            return this.addLab(newLab);
        } else {
            return this.updateLab(newLab);
        }
    },
    //根据id查询
    findOneLab(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/researchlab/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteLab(id) {
        return request({
            url: `/LabSystem/researchlab/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Researcher
    //按参数获取对象
    getLaberList(researcher) {
        return request({
            url: '/LabSystem/researcher/list',
            method: 'get',
            params: {
                pageNo: researcher.pageNo,
                pageSize: researcher.pageSize,
                researcherid: researcher.researcherid,
                labid: researcher.labid,
                researchername: researcher.researchername,
                gender: researcher.gender,
                level: researcher.level,
                age: researcher.age,
                researcharea: researcher.area
            }
        });
    },
    //新增对象
    addLaber(laber) {
        return request({
            url: '/LabSystem/researcher',
            method: 'post',
            data: laber
        });
    },
    //修改对象
    updateLaber(laber) {
        return request({
            url: '/LabSystem/researcher/update',
            method: 'put',
            data: laber
        })
    },
    //逻辑判断是新增还是修改
    saveLaber(laber) {
        if (laber.researcherid == null && laber.researcherid == undefined) {
            return this.addLaber(laber);
        } else {
            return this.updateLaber(laber);
        }
    },
    //根据id查询
    findOneLaber(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/researcher/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteLaber(id) {
        return request({
            url: `/LabSystem/researcher/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Outcome
    //按参数获取对象
    getOutcomeList(outcome) {
        return request({
            url: '/LabSystem/outcome/list',
            method: 'get',
            params: {
                pageNo: outcome.pageNo,
                pageSize: outcome.pageSize,
                outcomeid: outcome.outcomeid,
                projectid: outcome.projectid,
                outcomename: outcome.outcomename,
                achievedate: outcome.achievedate,
                type: outcome.type
            }
        });
    },
    //新增对象
    addOutcome(outcome) {
        return request({
            url: '/LabSystem/outcome',
            method: 'post',
            data: outcome
        });
    },
    //修改对象
    updateOutcome(outcome) {
        return request({
            url: '/LabSystem/outcome/update',
            method: 'put',
            data: outcome
        })
    },
    //逻辑判断是新增还是修改
    saveOutcome(outcome) {
        if (outcome.outcomeid == null && outcome.outcomeid == undefined) {
            return this.addOutcome(outcome);
        } else {
            return this.updateOutcome(outcome);
        }
    },
    //根据id查询
    findOneOutcome(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/outcome/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteOutcome(id) {
        return request({
            url: `/LabSystem/outcome/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Project
    //按参数获取对象
    getProjectList(project) {
        return request({
            url: '/LabSystem/project/list',
            method: 'get',
            params: {
                pageNo: project.pageNo,
                pageSize: project.pageSize,
                projectid: project.projectid,
                leaderid: project.leaderid,
                projectname: project.projectname,
                content: project.content,
                totalfund: project.totalfund,
                startdate: project.startdate,
                enddate: project.enddate
            }
        });
    },
    //新增对象
    addProject(project) {
        return request({
            url: '/LabSystem/project',
            method: 'post',
            data: project
        });
    },
    //修改对象
    updateProject(project) {
        return request({
            url: '/LabSystem/project/update',
            method: 'put',
            data: project
        })
    },
    //逻辑判断是新增还是修改
    saveProject(project) {
        if (project.projectid == null && project.projectid == undefined) {
            return this.addProject(project);
        } else {
            return this.updateProject(project);
        }
    },
    //根据id查询
    findOneProject(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/project/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteProject(id) {
        return request({
            url: `/LabSystem/project/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Space
    //按参数获取对象
    getSpaceList(officespace) {
        return request({
            url: '/LabSystem/officespace/list',
            method: 'get',
            params: {
                pageNo: officespace.pageNo,
                pageSize: officespace.pageSize,
                spaceid: officespace.spaceid,
                labid: officespace.labid,
                spacename: officespace.spacename,
                area: officespace.area,
                address: officespace.address
            }
        });
    },
    //新增对象
    addSpace(officespace) {
        return request({
            url: '/LabSystem/officespace',
            method: 'post',
            data: officespace
        });
    },
    //修改对象
    updateSpace(officespace) {
        return request({
            url: '/LabSystem/officespace/update',
            method: 'put',
            data: officespace
        })
    },
    //逻辑判断是新增还是修改
    saveSpace(officespace) {
        if (officespace.spaceid == null && officespace.spaceid == undefined) {
            return this.addSpace(officespace);
        } else {
            return this.updateSpace(officespace);
        }
    },
    //根据id查询
    findOneSpace(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/officespace/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteSpace(id) {
        return request({
            url: `/LabSystem/officespace/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Leader
    //按参数获取对象
    getLeaderList(leader) {
        return request({
            url: '/LabSystem/leader/list',
            method: 'get',
            params: {
                pageNo: leader.pageNo,
                pageSize: leader.pageSize,
                leaderid: leader.leaderid,
                workphone: leader.workphone,
                mobilephone: leader.mobilephone,
                email: leader.email
            }
        });
    },
    //新增对象
    addLeader(leader) {
        return request({
            url: '/LabSystem/leader',
            method: 'post',
            data: leader
        });
    },
    //修改对象
    updateLeader(leader) {
        return request({
            url: '/LabSystem/leader/update',
            method: 'put',
            data: leader
        })
    },
    //逻辑判断是新增还是修改
    saveLeader(leader) {
        if (leader.leaderid == null && leader.leaderid == undefined) {
            return this.addLeader(leader);
        } else {
            return this.updateLeader(leader);
        }
    },
    //根据id查询
    findOneLeader(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/leader/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteLeader(id) {
        return request({
            url: `/LabSystem/leader/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Director
    //按参数获取对象
    getDirectorList(director) {
        return request({
            url: '/LabSystem/director/list',
            method: 'get',
            params: {
                pageNo: director.pageNo,
                pageSize: director.pageSize,
                directorid: director.directorid,
                workphone: director.workphone,
                mobilephone: director.mobilephone,
                email: director.email
            }
        });
    },
    //新增对象
    addDirector(director) {
        return request({
            url: '/LabSystem/director',
            method: 'post',
            data: director
        });
    },
    //修改对象
    updateDirector(director) {
        return request({
            url: '/LabSystem/director/update',
            method: 'put',
            data: director
        })
    },
    //逻辑判断是新增还是修改
    saveDirector(director) {
        if (director.directorid == null && director.directorid == undefined) {
            return this.addDirector(director);
        } else {
            return this.updateDirector(director);
        }
    },
    //根据id查询
    findOneDirector(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/director/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteDirector(id) {
        return request({
            url: `/LabSystem/director/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Secretary
    //按参数获取对象
    getSecretaryList(secretary) {
        return request({
            url: '/LabSystem/secretary/list',
            method: 'get',
            params: {
                pageNo: secretary.pageNo,
                pageSize: secretary.pageSize,
                secretaryid: secretary.secretaryid,
                hiretime: secretary.hiretime,
                responsibility: secretary.responsibility,
            }
        });
    },
    //新增对象
    addSecretary(secretary) {
        return request({
            url: '/LabSystem/secretary',
            method: 'post',
            data: secretary
        });
    },
    //修改对象
    updateSecretary(secretary) {
        return request({
            url: '/LabSystem/secretary/update',
            method: 'put',
            data: secretary
        })
    },
    //逻辑判断是新增还是修改
    saveSecretary(secretary) {
        if (secretary.secretaryid == null && secretary.secretaryid == undefined) {
            return this.addSecretary(secretary);
        } else {
            return this.updateSecretary(secretary);
        }
    },
    //根据id查询
    findOneSecretary(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/secretary/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteSecretary(id) {
        return request({
            url: `/LabSystem/secretary/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Contact
    //按参数获取对象
    getContactList(contact) {
        return request({
            url: '/LabSystem/contact/list',
            method: 'get',
            params: {
                pageNo: contact.pageNo,
                pageSize: contact.pageSize,
                contactid: contact.contactid,
                workphone: contact.workphone,
                mobilephone: contact.mobilephone,
                email: contact.email
            }
        });
    },
    //新增对象
    addContact(contact) {
        return request({
            url: '/LabSystem/contact',
            method: 'post',
            data: contact
        });
    },
    //修改对象
    updateContact(contact) {
        return request({
            url: '/LabSystem/contact/update',
            method: 'put',
            data: contact
        })
    },
    //逻辑判断是新增还是修改
    saveContact(contact) {
        if (contact.contactid == null && contact.contactid == undefined) {
            return this.addContact(contact);
        } else {
            return this.updateContact(contact);
        }
    },
    //根据id查询
    findOneContact(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/contact/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteContact(id) {
        return request({
            url: `/LabSystem/contact/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Subproject
    //按参数获取对象
    getSubprojectList(subproject) {
        return request({
            url: '/LabSystem/subproject/list',
            method: 'get',
            params: {
                pageNo: subproject.pageNo,
                pageSize: subproject.pageSize,
                recordid: subproject.recordid,
                projectid: subproject.projectid,
                subprojectid: subproject.subprojectid,
                leaderid: subproject.leaderid,
                deadline: subproject.deadline,
                budget: subproject.budget,
                technicalindex: subproject.technicalindex
            }
        });
    },
    //新增对象
    addSubproject(subproject) {
        return request({
            url: '/LabSystem/subproject',
            method: 'post',
            data: subproject
        });
    },
    //修改对象
    updateSubproject(subproject) {
        return request({
            url: '/LabSystem/subproject/update',
            method: 'put',
            data: subproject
        })
    },
    //逻辑判断是新增还是修改
    saveSubproject(subproject) {
        if (subproject.recordid == null) {
            return this.addSubproject(subproject);
        } else {
            return this.updateSubproject(subproject);
        }
    },
    //根据id查询
    findOneSubproject(pid, sid) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/subproject/getpid=${pid}sid=${sid}`,
            method: 'get'
        });
    },
    //删除对象
    deleteSubproject(pid, sid) {
        return request({
            url: `/LabSystem/subproject/deletepid=${pid}sid=${sid}`,
            method: 'delete'
        });
    },

    //Record
    //按参数获取对象
    getRecordList(record) {
        return request({
            url: '/LabSystem/record/list',
            method: 'get',
            params: {
                pageNo: record.pageNo,
                pageSize: record.pageSize,
                recordid: record.recordid,
                projectid: record.projectid,
                researcherid: record.researcherid,
                participationdate: record.participationdate,
                workload: record.workload,
                allocatedfund: record.allocatedfund
            }
        });
    },
    //新增对象
    addRecord(record) {
        return request({
            url: '/LabSystem/record',
            method: 'post',
            data: record
        });
    },
    //修改对象
    updateRecord(record) {
        return request({
            url: '/LabSystem/record/update',
            method: 'put',
            data: record
        })
    },
    //逻辑判断是新增还是修改
    saveRecord(record) {
        if (record.recordid == null && record.record == undefined) {
            return this.addRecord(record);
        } else {
            return this.updateRecord(record);
        }
    },
    //根据id查询
    findOneRecord(pid, rid) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/record/getpid=${pid}&rid=${rid}`,
            method: 'get'
        });
    },
    //删除对象
    deleteRecord(pid, rid) {
        return request({
            url: `/LabSystem/record/deletepid=${pid}&rid=${rid}`,
            method: 'delete'
        });
    },

    //Department
    //按参数获取对象
    getDepartmentList(department) {
        return request({
            url: '/LabSystem/department/list',
            method: 'get',
            params: {
                pageNo: department.pageNo,
                pageSize: department.pageSize,
                departmentid: department.departmentid,
                leaderid: department.leaderid,
                departmentname: department.departmentname,
                address: department.address,
                type: department.type
            }
        });
    },
    //新增对象
    addDepartment(department) {
        return request({
            url: '/LabSystem/department',
            method: 'post',
            data: department
        });
    },
    //修改对象
    updateDepartment(department) {
        return request({
            url: '/LabSystem/department/update',
            method: 'put',
            data: department
        })
    },
    //逻辑判断是新增还是修改
    saveDepartment(department) {
        if (department.departmentid == null && department.departmentid == undefined) {
            return this.addDepartment(department);
        } else {
            return this.updateDepartment(department);
        }
    },
    //根据id查询
    findOneDepartment(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/department/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deleteDepartment(id) {
        return request({
            url: `/LabSystem/department/deleteid=${id}`,
            method: 'delete'
        });
    },

    //Contributor
    //按参数获取对象
    getContributorList(contributor) {
        return request({
            url: '/LabSystem/contributor/list',
            method: 'get',
            params: {
                pageNo: contributor.pageNo,
                pageSize: contributor.pageSize,
                recordid: contributor.recordid,
                outcomeid: contributor.outcomeid,
                researcherid: contributor.researcherid,
                ranks: contributor.rank
            }
        });
    },
    //新增对象
    addContributor(contributor) {
        return request({
            url: '/LabSystem/contributor',
            method: 'post',
            data: contributor
        });
    },
    //修改对象
    updateContributor(contributor) {
        return request({
            url: '/LabSystem/contributor/update',
            method: 'put',
            data: contributor
        })
    },
    //逻辑判断是新增还是修改
    saveContributor(contributor) {
        if (contributor.recordid == null && contributor.recordid == undefined) {
            return this.addContributor(contributor);
        } else {
            return this.updateContributor(contributor);
        }
    },
    //根据id查询
    findOneContributor(oid, rid) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/contributor/getoid=${oid}&rid=${rid}`,
            method: 'get'
        });
    },
    //删除对象
    deleteContributor(oid, rid) {
        return request({
            url: `/LabSystem/contributor/deleteoid=${oid}&rid=${rid}`,
            method: 'delete'
        });
    },

    //ContactRelation
    //按参数获取对象
    getContactrelationList(contactrelation) {
        return request({
            url: '/LabSystem/contactrelation/list',
            method: 'get',
            params: {
                pageNo: contactrelation.pageNo,
                pageSize: contactrelation.pageSize,
                recordid: contactrelation.recordid,
                contactid: contactrelation.contactid,
                departmentid: contactrelation.departmentid,
                departmentname: contactrelation.departmentname
            }
        });
    },
    //新增对象
    addContactrelation(contactrelation) {
        return request({
            url: '/LabSystem/contactrelation',
            method: 'post',
            data: contactrelation
        });
    },
    //修改对象
    updateContactrelation(contactrelation) {
        return request({
            url: '/LabSystem/contactrelation/update',
            method: 'put',
            data: contactrelation
        })
    },
    //逻辑判断是新增还是修改
    saveContactrelation(contactrelation) {
        if (contactrelation.recordid == null && contactrelation.recordid == undefined) {
            return this.addContactrelation(contactrelation);
        } else {
            return this.updateContactrelation(contactrelation);
        }
    },
    //根据id查询
    findOneContactrelation(cid, did) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/contactrelation/getcid=${cid}did=${did}`,
            method: 'get'
        });
    },
    //删除对象
    deleteContactrelation(cid, did) {
        return request({
            url: `/LabSystem/contactrelation/deletecid=${cid}did=${did}`,
            method: 'delete'
        });
    },

    //Pdrelation
    //按参数获取对象
    getPdrelationList(pdrelation) {
        return request({
            url: '/LabSystem/pdrelation/list',
            method: 'get',
            params: {
                pageNo: pdrelation.pageNo,
                pageSize: pdrelation.pageSize,
                recordid: pdrelation.recordid,
                projectid: pdrelation.projectid,
                projectname: pdrelation.projectname,
                partner: pdrelation.partner,
                principle: pdrelation.principle,
                qualifier: pdrelation.qualifier
            }
        });
    },
    //新增对象
    addPdrelation(pdrelation) {
        return request({
            url: '/LabSystem/pdrelation',
            method: 'post',
            data: pdrelation
        });
    },
    //修改对象
    updatePdrelation(pdrelation) {
        return request({
            url: '/LabSystem/pdrelation/update',
            method: 'put',
            data: pdrelation
        })
    },
    //逻辑判断是新增还是修改
    savePdrelation(pdrelation) {
        if (pdrelation.recordid == null && pdrelation.recordid == undefined) {
            return this.addPdrelation(pdrelation);
        } else {
            return this.updatePdrelation(pdrelation);
        }
    },
    //根据id查询
    findOnePdrelation(id) {
        return request({
            //使用``能包含${}变量
            url: `/LabSystem/pdrelation/getid=${id}`,
            method: 'get'
        });
    },
    //删除对象
    deletePdrelation(id) {
        return request({
            url: `/LabSystem/pdrelation/deleteid=${id}`,
            method: 'delete'
        });
    },
}