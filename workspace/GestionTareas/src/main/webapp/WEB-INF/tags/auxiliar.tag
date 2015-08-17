<!-- Datos de la Instalacion - Start -->
<div class="panel panel-default">
    <div class="panel-body">
        <div class="row">
            <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                <div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
                    <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ">
                        Instalación :
                    </label>
                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                        <input type="text"
                               id="ninstalacion"
                               name="ninstalacion"
                               class="form-control input-custom-global"
                               ng-model="tarea.numeroInstalacion"
                               maxlength=""
                               pattern=""
                               disabled
                                />
                    </div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <input type="button" class="btn btn-xs" value="Buscar Instalación" />
                </div>
            </div>
            <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ">
                    Titular :
                </label>
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <input type="text"
                           id="titular"
                           name="titular"
                           class="form-control input-custom-global"
                           ng-model="installationData.titular"
                           maxlength=""
                           pattern=""
                           disabled
                            />
                </div>
            </div>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ">
                    Persona de contacto :
                </label>
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <input type="text"
                           id="personaContacto"
                           name="personaContacto"
                           class="form-control input-custom-global"
                           ng-model="tarea.personaContacto"
                           maxlength=""
                           pattern=""
                           disabled
                            />
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ">
                    Panel :
                </label>
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <input type="text"
                           id="panel"
                           name="panel"
                           class="form-control input-custom-global"
                           ng-model="installationData.panel"
                           maxlength=""
                           pattern=""
                           disabled
                            />
                </div>
            </div>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ">
                    Teléfono :
                </label>
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <input type="text"
                           id="telefono"
                           name="telefono"
                           class="form-control input-custom-global"
                           ng-model="tarea.telefono"
                           maxlength=""
                           pattern=""
                           disabled
                            />
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ">
                    Versión :
                </label>


                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <input type="text"
                           id="version"
                           name="version"
                           class="form-control input-custom-global"
                           ng-model="installationData.version"
                           maxlength=""
                           pattern=""
                           disabled
                            />
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Datos de la Instalacion - End -->
