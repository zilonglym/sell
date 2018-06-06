//
//  main.js
//
//  Copyright (c) 2010 Microsoft Corporation.
//
//  Bing client runtime bootstrap script.
//



//
//  Set the unhandled exception handler first thing
//
system.scripting.onerror = function system_scripting_onerror (msg, url, linenumber)
{
    // Need to wrap this whole function in a try/catch because an 
    // unhandled exception in this unhandled exception handler will
    // cause an infinite loop
    try
    {
        // system.debugging.enabled would be true only for debug builds.
        // The enterDebugger() statement here is for the test team to 
        // have a debugger window when an unhandled expception occurs
        // and they have a chance to debug the rest of the code block.
        if (system.debugging.enabled)
        {
            system.debugging.enterDebugger();
        }

        var maxdepth = 100;
        var callstackArray = [];
        var currentFunction = arguments.callee.caller;
        while (currentFunction && (maxdepth-- > 0))
        {
            var fn = currentFunction.toString();
            var fnameStart = fn.indexOf('function ') + 9;
            var fnameEnd = fn.indexOf('(', fnameStart);
            var fname = fn.substring(fnameStart, fnameEnd) || 'anonymous';
            callstackArray.push(fname);
            currentFunction = currentFunction.caller;
        }


        //
        //  We've seen cases of this function looping infinitely, which
        //  causes our memory usage to grow unbounded.  We don't want to
        //  DoS the machine, but we'd still like an opportunity to jump
        //  in and see what all the hubbub is about.
        //
        if ((maxdepth == -1) && system.debugging.enabled)
        {
            system.debugging.enterDebugger();
        }


        var callStack = callstackArray.join('\r\n');
        system.instrumentation.fatalErrorLog("00000000-0000-0000-0000-000000000000",
                                             "0.0.0.0",
                                             msg,
                                             url,
                                             linenumber,
                                             callStack,
                                             "");
    }

    catch (e)
    {
        if (system.debugging.enabled)
        {
            system.debugging.enterDebugger();
        }
    }
};



function __internal()
{
}


//
//  There are properties that we reference frequently but that never
//  change, so we declare these at global scope and populate them at
//  the end of initialization.
//
var s_ig;
var s_iga;
var s_ign;
var sube;
var beforeNavigate;
var documentComplete;
var frameComplete;
var windowStateChanged;
var toolbarShown;
var toolbarHidden;
var sessionStart;
var sessionEnd;
var toolbarButtonClick;
var wingClosed;
var searchPerformed;
__internal.assignInvariants = function __internal_assignInvariants ()
{
    s_ig               = system._internal.globals;
    s_iga              = s_ig.appManager;
    s_ign              = s_ig.notificationManager;
    sube               = system.ui.browser.events;
    beforeNavigate     = sube.beforeNavigate;
    documentComplete   = sube.documentComplete;
    frameComplete      = sube.frameComplete;
    windowStateChanged = sube.windowStateChanged;
    toolbarShown       = sube.toolbarShown;
    toolbarHidden      = sube.toolbarHidden;
    sessionStart       = sube.sessionStart;
    sessionEnd         = sube.sessionEnd;
    toolbarButtonClick = sube.toolbarButtonClick;
    wingClosed         = sube.wingClosed;
    searchPerformed    = sube.searchPerformed;
};


//
// Scripts that will be loaded into global scope and will be available
// for all apps to consume.
//

// the min-script set corresponds to only scripts which are loaded prior
// to the runtime being fully ready for use, such as when completing the
// installation before the runtime is ready to start all the services.
__internal._minScripts =
    [
        "io\\io.js",
        "parsers\\json2.js",
        "system\\runtimesettings.js",
        "system\\appinstaller.js",
        "parsers\\xmlparser.js",
        "parsers\\activeversionxml.js",
        "parsers\\appmanifestxml.js",
        "system\\app.js",
        "utility\\utility.js",
        "objectmodel\\configurationapi.js",
        "system\\configcacheproperties.js",
        "system\\configcache.js",
        "system\\buttonlayout.js"
    ];

__internal._fullScripts =
    [
        "io\\downloader.js",
        "io\\websocket.js",
        "system\\apppackage.js",
        "system\\blockedapp.js",
        "system\\appcatalog.js",
        "system\\appcatalogcache.js",
        "system\\flightmanager.js",
        "system\\appmanager.js",
        "objectmodel\\appapi.js",
        "objectmodel\\apppackageapi.js",
        "objectmodel\\appmanagementapi.js",
        "objectmodel\\buttonapi.js",
        "objectmodel\\servicesapi.js",
        "objectmodel\\notificationapi.js",
        "system\\toast.js",
        "system\\toasthubQueue.js",
        "system\\toasthub.js",
        "system\\platformsettings.js"
    ];

//
// constants
//
__internal._kServiceStartIntervalInMin = 1440; // 24 hours

//
// properties
//
__internal._minInitializationDone = false;

__internal.defaultServiceSettingValues =
    {
        FlightServiceUrl: "http://bbcore.cloudapp.net/flights/getflightassignment",
        CatalogUrl: "http://bbcore.cloudapp.net/catalogs/getcatalog",
        BrandCatalogUrl: "http://bbcore.cloudapp.net/catalogs/getbrandcatalog",
        FeaturedUrl: "http://bbcore.cloudapp.net/featured/getfeatured",
        WingPosition: "1",
        ToastPosition: "0",
        MaxLeftButtons: "5",
        UpdateServiceStartIntervalInMin: __internal._kServiceStartIntervalInMin.toString(),
        MaxSearchWidth: "350",
        MinSearchWidth: "150",
        LeftAlignUI: "0",
        MaxRepairCount: "3",
        HoverPreviewAvailable: "0",
        ButtonEffect: "0",
        HoverEffect: "0",
        DefaultSearchText: "0",
        SearchButtonWidth: "70"
    };
    
//
// Entry point function called by the native runtime so that that the
// runtime host can execute any steps required in order to complete
// the installation of the toolbar for the current user.
//
__internal.onClientLoad = function __internal_onClientLoad ()
{
    if (!__internal.runMinInitialization())
    {
        system.debugging.tracing.traceError("main.js", "onClientLoad(): Failed to initialize.");
        return false;
    }

    if (__internal.detectAppCopyRequired())
    {
        if (!__internal.copyDefaultApps())
        {
            system.debugging.tracing.traceError("main.js", "onClientLoad(): Failed to copy default apps.");
            return false;
        }

        // record that the copy was done
        system._internal.globals.localSettings.set("AppsCopied", true);
    }

    // Initialize system.ui.browser.sessions. For us, it is effectively a map<sessionId, info>
    // where info is a JS object with session-specific information, such as the toolband HWND
    // that is used by some system.* function implementations.
    system.ui.browser.sessions = {};

    return true;
}

//
// Entry point function called by the native runtime so that the script
// runtime can initialize, note we don't want to catch any exceptions here
// so that the native runtime can know if the script runtime initialized 
// properly.
//

__internal.initialize = function __internal_initialize ()
{
    if (!__internal.loadScripts())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to load scripts. Cannot initialize Runtime.");
        return false;
    }

    if (!__internal.initializeFlightManager())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to initialize Flight Manager. Cannot initialize Runtime.");
        return false;
    }

    if (!__internal.initializeServiceSettings())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to initialize service settings. Cannot initialize Runtime.");
        return false;
    }

    if (!__internal.initializeAppManager())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to initialize App Manager. Cannot initialize Runtime.");
        return false;
    }

    if (!__internal.initializeNotifications())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to initialize Notifications. Cannot initialize Runtime.");
        return false;
    }

    if (!__internal.runAppManager())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to run App Manager. Cannot initialize Runtime.");
        return false;
    }

    if (!__internal.scheduleTasks())
    {
        system.debugging.tracing.traceError("main.js", "initialize(): Failed to Schedule Tasks. Cannot initialize Runtime.");
        return false;
    }

    __internal.assignInvariants();

    system.debugging.tracing.traceInfo("main.js", "initialize(): completed.");

    return true;
}

//
//  Shutdown function called by the native runtime so that that the script
//  runtime can uninitialize and cleanup
//
__internal.onClientShutdown = function __internal_onClientShutdown()
{
    //
    // Save service settings in property bag. This is useful when a new platform.config is downloaded for a flight.
    //
    __internal.saveServiceSettings();

    var services;
    try { services = system.services; }
    catch (e) { services = null; }

    if (services)
    {
        services.events._raiseEvent(system.services.events.shutdown);
    }


    var appmanager;
    try { appmanager =  system._internal.globals.appManager; }
    catch (e) { appmanager = null; }

    if (appmanager)
    {
        appmanager.shutdown();
    }
}

__internal.runMinInitialization = function __internal_runMinInitialization ()
{
    if (__internal._minInitializationDone)
    {
        return true;
    }

    system._internal.globals = new Object();

    // initialize tracing first so that other scripts can use tracing in their
    // initialization phase
    __internal.initializeTracing();

    if (!__internal.loadMinScriptSet())
    {
        system.debugging.tracing.traceError("main.js", "runMinInitialization(): Failed to load script set.");
        return false;
    }

    if (!__internal.initializeFolders())
    {
        system.debugging.tracing.traceError("main.js", "runMinInitialization(): Failed to initialize folders.");
        return false;
    }

    if (!__internal.initializeLocalSettings())
    {
        system.debugging.tracing.traceError("main.js", "runMinInitialization(): Failed to initialize local settings.");
        return false;
    }

    __internal._minInitializationDone = true;

    return true;
}

__internal.loadMinScriptSet = function __internal_loadMinScriptSet ()
{
    var installDir = system._internal.config.installFolderPath;
    if (installDir.charAt(installDir.length - 1) != '\\')
    {
        //
        //  This is necessary because the IDebug* interfaces are
        //  hopelessly brittle when it comes to dealing with extra
        //  backslashes (or, heaven forbid, a forward slash).  If the
        //  path name contains two adjacent backslashes, the file will
        //  not appear in the debugger.  The reason we do this by hand
        //  like this is that io.js hasn't been loaded yet.
        //
        installDir += '\\';
    }

    for (var i = 0; i < __internal._minScripts.length; i++)
    {
        try
        {
            system.scripting.loadFile(installDir + "scripts\\" + __internal._minScripts[i]);
        }
        catch (e)
        {
            system.debugging.tracing.traceError("main.js", "loadMinScriptSet(): Failed to load " + __internal._minScripts[i] + ".");
            return false;
        }
    }

    return true;
}

__internal.getInstallDate = function __internal_getInstallDate()
{
    var installDate = "dbg-install";
    try
    {
        installDate = system._internal.config.installDate;
    }
    catch (e)
    {
        system.debugging.tracing.traceError("main.js", "getInstallDate(): Failed to retrieve install date.");
    }

    return installDate;
}

__internal.detectAppCopyRequired = function __internal_detectAppCopyRequired()
{
    var copyApps = false;

    try
    {
        var installDate = __internal.getInstallDate();

        var lastCopyDate = system._internal.globals.localSettings.get("LastAppCopy");

        // if there is no previous copy date or if the install date changes, copy the apps
        if (!lastCopyDate || lastCopyDate != installDate)
        {
            // record the latest install date
            system._internal.globals.localSettings.set("LastAppCopy", installDate);

            copyApps = true;
        }

        if (!copyApps)
        {
            var alreadyCopied = system._internal.globals.localSettings.get("AppsCopied");
            if (!alreadyCopied)
            {
                copyApps = true;
            }
        }
    }
    catch (e)
    {
        system.debugging.tracing.traceError("main.js", "detectAppCopyRequired(): unexpected exception. using copyApps=true. details: " + e.message);

        // fail safe, assuming we should copy
        copyApps = true;
    }

    if (copyApps)
    {
        // since we'll be copying now, record the fact that they have not been copied
        system._internal.globals.localSettings.set("AppsCopied", false);
    }

    return copyApps;
}

__internal.copyDefaultApps = function __internal_copyDefaultApps ()
{
    try
    {
        system.debugging.tracing.tracePerf("main.js", "_copyDefaultApps(): starting copy of default apps.");

        var result = AppInstaller.installDefaultApps();

        system.debugging.tracing.tracePerf("main.js", "_copyDefaultApps(): finished copy of default apps.");

        return result;
    }
    catch (e)
    {
        system.debugging.tracing.traceError("main.js", "_copyDefaultApps(): unexpected error " + e.message);
        return false;
    }
}

__internal.loadScripts = function __internal_loadScripts ()
{
    var installDir = system._internal.config.installFolderPath;
    var scriptDir = system.io.path.append(installDir, "scripts");
    for (var i = 0; i < __internal._fullScripts.length; i++)
    {
        try
        {
            system.scripting.loadFile(system.io.path.append(scriptDir, __internal._fullScripts[i]));
        }
        catch (e)
        {
            system.debugging.tracing.traceError("main.js", "loadScripts(): Failed to load " + __internal._fullScripts[i] + ".");
            return false;
        }
    }
    
    var debugFile = system.io.path.append(scriptDir, "debug\\EnableDebugging.js");
    if (system.io.file.exists(debugFile))
    {
        system.scripting.loadfile(debugFile);
    }


    return true;
}

//
// Summary: Initializes the notifications system. The bingclient.ui.notifications object is a simple api
//          wrapper around the notifications system that allows for hiding of internal methods and data.
//          Any internal system related component should use system._internal.globals.notificationManager to perform
//          operations on the notifications system
//
// Params:  None
//
__internal.initializeNotifications = function __internal_initializeNotifications ()
{
    try
    {
        system._internal.globals.notificationManager = new Notifications();
        system.ui.notifications = new NotificationsApi();
        system._internal.globals.notificationManager._initialize();
        return true;
    }
    catch (e)
    {
        system.debugging.tracing.traceError("main.js", "initializeNotifications(): Error: " + e.message);
        return false;
    }
}

__internal.initializeTracing = function __internal_initializeTracing ()
{
    system.scripting.loadFile(system._internal.config.installFolderPath + "\\scripts\\debug\\trace.js");
    system.debugging.tracing = new TraceApi(system.debugging, system._internal.config.tracingEnabled);
}

__internal.initializeFolders = function __internal_initializeFolders ()
{
    var sip  = system.io.path;
    var sid  = system.io.directory;
    var dirs =
    [
        sip.getSystemLocalPath(),
        sip.getBingClientAppsPath(),
        sip.getBingClientTempPath(),
        sip.getBingClientSystemPath(),
        sip.getBingClientFlightsPath()
    ];

    for (var i = 0; i < dirs.length; i++)
    {
        if (sid.exists(dirs[i]))
        {
            continue;
        }

        sid.create(dirs[i]);
    }

    return true;
}

__internal.setProtectedModeFolder = function __internal_setProtectedModeFolder()
{
    var dir = system.io.path.getSystemLocalPath();

    if (system.io.directory.exists(dir))
    {
        this._setProtectedModeFiles(dir);
    }
}

__internal._setProtectedModeFiles = function __internal__setProtectedModeFiles (dir)
{
    var sid = system.io.directory;

    system._internal.SetProtectedModeFileFolder(dir);

    var files = sid.getFiles(dir);
    for (var i = 0; i < files.length; i++)
    {
        system._internal.SetProtectedModeFileFolder(files[i]);
    }

    var directories = sid.getDirectories(dir);
    for (i = 0; i < directories.length; i++)
    {
        this._setProtectedModeFiles(directories[i]);
    }
}

__internal.initializeFlightManager = function __internal_initializeFlightManager ()
{
    system._internal.globals.flightManager = new FlightManager();
    return system._internal.globals.flightManager.initialize();
}

__internal.initializeLocalSettings = function __internal_initializeLocalSettings ()
{
    // initialize the global settings object
    var path = system.io.path.append(system.io.path.getBingClientSystemPath(), "local-settings.json");

    var defaultValues =
    {
        Locale: null,
        Market: null,
        BrandCode: null,
        RuntimeVersion: null,
        Flight: null,
        Catalog: null,
        BrandCatalog: null,
        FirstRun: true,
        InstallDate: null,
        Corpnet: null
    };

    system._internal.globals.localSettings = new RuntimeSettings(path, defaultValues);

    // determine if the Toolbar should run in development mode.
    var developmentMode = false;
    var devFilePath = system.io.path.append(system.io.path.getSystemLocalPath(), "development.txt");

    if (system.io.file.exists(devFilePath))
    {
        system.debugging.tracing.traceInfo("main.js", "initializeLocalSettings(): Running in Development mode.");
        developmentMode = true;
        var file = null;
        try
        {
            file = system.io.file.openText(devFilePath, "w");
            file.write("Development mode.");
        }
        catch (e)
        {
        }
        finally
        {
            if (file)
            {
                file.close();
            }
        }
    }

    system._internal.globals.localSettings.set("Development", developmentMode);
    return true;
}

__internal.initializeServiceSettings = function __internal_initializeServiceSettings()
{
    // initialize the service settings object
    var path = system.io.path.append(system.io.path.getBingClientSystemPath(), "service-settings.json");

    // determine if we should refresh the service-settings.json if it already exists
    if (system.io.file.exists(path))
    {
        var installDate = __internal.getInstallDate();
        var lastUpdate = system._internal.globals.localSettings.get("LastServiceSettingsUpdate");

        if (lastUpdate != "dbg-install") // this is a test hook to prevent the file from being deleted
        {
            // if there is no previous date or if the install date changes, re-create the file
            if (!lastUpdate || lastUpdate != installDate)
            {
                // record the latest update date
                system._internal.globals.localSettings.set("LastServiceSettingsUpdate", installDate);

                // delete the existing version
                system.io.file.deleteFile(path, true);
            }
        }
    }

    // initializing the service settings require the flight manager to be initialized.
    path = system._internal.globals.flightManager.getFlightedFilePath("platform.config", path);

    system._internal.globals.serviceSettings = new RuntimeSettings(path, __internal.defaultServiceSettingValues);

    // copy all values from the service config into the property bg we share between
    // the script and native components.
    var keys = system._internal.globals.serviceSettings.getKeys();
    var platSettings = new PlatformSettings();

    platSettings.destroy(); // remove previous values

    for (var i = 0; i < keys.length; i++)
    {
        var name = keys[i];
        var value = system._internal.globals.serviceSettings.get(keys[i]);

        platSettings.write(name, value);
    }

    return true;
}

__internal.initializeAppManager = function __internal_initializeAppManager ()
{
    system._internal.globals.appManager = new AppManager();
    system._internal.globals.appManager.initialize();

    // I know what you're thinking: what do buttons have to do with
    // app management?  Apparently, quite a bit: every single method
    // on the button API just delegates into the app manager.
    system.ui.button = new ButtonApi();

    // set the appmanagement api before running the apps
    //
    // this object will be passed to the service hosts to allow apps
    // to perform app-management tasks.  this is necessary because the
    // global settings panel is itself an app.
    system.appmanagement = new AppManagementApi();

    return true;
}

__internal.runAppManager = function __internal_runAppManager ()
{
    // run the apps
    system.debugging.tracing.traceInfo("main.js", "runAppManager(): running apps.");
    system._internal.globals.appManager.runApps();

    // execute any post-initialization code
    system.debugging.tracing.traceInfo("main.js", "runAppManager(): executing postInitialization.");
    system._internal.globals.appManager.postInitialize();

    return true;
}

__internal.scheduleTasks = function __internal_scheduleTasks ()
{
    // schedule a timer for starting the Update Service a few (30) seconds
    // after the runtime is running.
    system.scripting.timers.setTimeout(function () { __internal.startUpdateService(); }, 30 * 1000);
    return true;
}

__internal.startUpdateService = function __internal_startUpdateService ()
{
    // get the configurable start time interval
    var startIntervalInMin = parseInt(system._internal.globals.serviceSettings.get("UpdateServiceStartIntervalInMin"));
    if (isNaN(startIntervalInMin))
    {
        startIntervalInMin = __internal._kServiceStartIntervalInMin;
    }

    // get when we last updated the service
    var lastUpdate = system._internal.globals.localSettings.getWithDefault("LastUpdateServiceRun", "never");

    // convert the lastUpdate value from a string to a Date object
    lastUpdate = new Date(lastUpdate);
    if (isNaN(lastUpdate))
    {
        lastUpdate = new Date(0); // use the earliest javascript date value
    }

    // check if it is time to update
    if ((new Date()) - startIntervalInMin * 60 * 1000 > lastUpdate)
    {
        try
        {
            system.debugging.tracing.traceInfo("main.js", "startUpdateService(): starting the Update Service.");
            system._internal.config.startUpdateService();

            // remember when we last started the service
            // make sure to call .toString() otherwise it doesn't serialize correctly
            system._internal.globals.localSettings.set("LastUpdateServiceRun", (new Date()).toString());
        }
        catch (e)
        {
            system.debugging.tracing.traceError("main.js", "startUpdateService(): Error: " + e.message);
        }
    }

    // schedule the timer for the next check
    system.scripting.timers.setTimeout(function () { __internal.startUpdateService(); }, startIntervalInMin * 60 * 1000);

    return;
}

//
//  Called by the native runtime to pass in events.
//
__internal.dispatchEvent = function __internal_dispatchEvent (evt)
{
    var session   = null;
    var sessionId = 0;
    var apps      = s_iga.getAllApps();
    var evtbuf    = __internal._getJsonForEvent(evt);


    s_ign.browserEventSink();


    if (evt.eventId == sessionStart)
    {
        session = evt.param("sessionObject");
        var hwnd = evt.param("hwnd");
        system.ui.browser.sessions[evt.sessionId] = { "hwnd": hwnd, "sessionObject": session };
    }

    if (evt.eventId == sessionEnd)
    {
        sessionId = evt.sessionId;
        delete system.ui.browser.sessions[sessionId];
    }

    for (var i = 0; i < apps.length; i++)
    {
        //
        //  If scripts haven't been loaded, there's no point in sending events.
        //
        if (!apps[i].running)
        {
            continue;
        }

        if (session != null)
        {
            apps[i].registerSession(evt.sessionId, session);
        }

        apps[i].executeScript("__internal.dispatchEvent(" + evtbuf + ");");

        if (sessionId != 0)
        {
            apps[i].executeScript("__internal.unregisterSession(" + sessionId + ");");
        }
    }
};



__internal._getJsonForEvent = function __internal__getJsonForEvent (evt)
{
    //
    //  Our event objects do not support IDispatchEx (they should, but
    //  we haven't implemented it yet), so we cannot hand them
    //  directly to JSON.  Instead, we do the mapping into a
    //  javascript object (which, underneath, does support
    //  IDispatchEx) and hand that to JSON.
    //
    //  This isn't great because it means that we must have total
    //  knowledge here of all events because event.params() isn't
    //  enumerable -- you have know what string keys to supply, which
    //  varies based on the type of event (e.g., the event
    //  toolbarButtonClick does not have a "url" parameter).
    //
    //  On the positive side, the event objects that we create here
    //  and send to apps ARE enumerable and thus browseable from the
    //  debugger: rather than having to know what string to pass to
    //  evt.param(), app writers can just examine the event objects to
    //  see what they contain.
    //
    //  We'll solve the unenumerable event problem later.
    //
    var jsonevent =
    {
        sessionId: evt.sessionId,
        eventId:   evt.eventId,
        params:    {}
    };

    var params = jsonevent.params;
    switch (evt.eventId)
    {
    case beforeNavigate:
        params.url = evt.param("url");
        break;

    case documentComplete:
        params.url         = evt.param("url");
        params.cookie      = evt.param("cookie");
        params.referrer    = evt.param("referrer");
        params.tabid       = evt.param("tabid");
        params.privateMode = evt.param("privateMode");
        break;

    case frameComplete:
        params.url     = evt.param("url");
        params.pageUrl = evt.param("pageUrl");
        break;

    case windowStateChanged:
        params.visible = evt.param("visible");
        break;

    case toolbarShown:
    case toolbarHidden:
    case sessionStart:
    case sessionEnd:
        break;

    case toolbarButtonClick:
        params.appId = evt.param("AppId");
        system._internal.globals.appManager.onButtonClick(params.appId);
        break;

    case wingClosed:
        params.appId = evt.param("appid");
        break;

    case searchPerformed:
        params.url = evt.param("url");
        break;

    default:
        throw new Error("Unknown event " + evt.eventId + " from session " + evt.sessionId);
    }


    //
    //  Map the new event to a JSON representation and pass it up.
    //
    return JSON.stringify(jsonevent);
};

__internal.saveServiceSettings = function __internal_saveServiceSettings()
{
    // 
    // get the path of platform.config and if it is present save service settings in registry store.
    //
    var path = system.io.path.append(system.io.path.getBingClientSystemPath(), "service-settings.json");
    var flightedPath = system._internal.globals.flightManager.getFlightedFilePath("platform.config", path);
    if (path == flightedPath)
    {
        //
        // platform.config is not present so current state is already saved. Bail out.
        //
        return;
    }

    system._internal.globals.serviceSettings = new RuntimeSettings(flightedPath, __internal.defaultServiceSettingValues);

    //
    // copy all values from the service config into the property bag we share between
    // the script and native components.
    //
    var keys = system._internal.globals.serviceSettings.getKeys();
    var platSettings = new PlatformSettings();

    //
    // remove previous values
    //
    platSettings.destroy();

    for (var i = 0; i < keys.length; i++)
    {
        var name = keys[i];
        var value = system._internal.globals.serviceSettings.get(keys[i]);

        platSettings.write(name, value);
    }
}
