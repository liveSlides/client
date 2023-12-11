package com.harun.liveSlide.network;

import com.harun.liveSlide.screens.mainScreen.AuthLayoutController;
import com.harun.liveSlide.components.pdfViewer.PDFViewer;

public class NetworkManager implements INetworkSenderManager , INetworkReceiverManager {
    private final AuthLayoutController authLayoutController;
    private final PDFViewer pdfViewer;

    public NetworkManager(AuthLayoutController authLayoutController, PDFViewer pdfViewer) {
        this.authLayoutController = authLayoutController;
        this.pdfViewer = pdfViewer;
    }
}
