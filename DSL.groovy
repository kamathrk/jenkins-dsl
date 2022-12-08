import groovy.json.JsonSlurper

def reposJSON = new JsonSlurper().parseText(readFileFromWorkspace('repos.json'))

reposJSON.repos.each {
    createPipeline(it)
}

void createPipeline(it) {

    def jobname = it.jobname
    def gitrepo = it.gitrepo
    def desc = it.description
    pipelineJob(jobname) {
        description(desc)

        parameters {
        activeChoiceParam('CHOICE_1') {
            description('Allows user choose from multiple choices')
            filterable()
            choiceType('SINGLE_SELECT')
            groovyScript {
                script('["MALE", "FEMALE"]')
                fallbackScript('"NOT SURE"')
            }
        }
        activeChoiceParam('CHOICE-2') {
            description('Multiple choices')
            filterable()
            choiceType('SINGLE_SELECT')
            groovyScript {
                script('["choice1", "choice2"]')
                fallbackScript('"fallback choice"')
            }
        }
        }
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(gitrepo)
                        }
                    }
                }
            }
        }
    }
}
