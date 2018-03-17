function showTab(tabName){
    var tabPages = document.getElementsByClassName("tabpage");
    for (i = 0; i < tabPages.length; i++) {
        tabPages[i].style.display = "none";
    }
    var selectedTab = document.getElementById(tabName);
    selectedTab.style.display = "block";
}
