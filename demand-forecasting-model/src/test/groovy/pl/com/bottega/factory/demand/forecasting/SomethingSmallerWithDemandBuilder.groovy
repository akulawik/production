package pl.com.bottega.factory.demand.forecasting

import java.time.LocalDate

class SomethingSmallerWithDemandBuilder {

    SomethingSmallerWithDemand build() {
        // todo
    }

    SomethingSmallerWithDemandBuilder reset() {
        nothingDemanded()
        noAdjustments()
    }

    Object asType(Class clazz) {
        clazz == SomethingSmallerWithDemand ? build() : super.asType(clazz)
    }

    SomethingSmallerWithDemandBuilder date(LocalDate date) {
        // todo
        this
    }

    SomethingSmallerWithDemandBuilder nothingDemanded() {
        // todo
        this
    }

    SomethingSmallerWithDemandBuilder noAdjustments() {
        // todo
        this
    }

    SomethingSmallerWithDemandBuilder demandedLevels(long level) {
        // todo
        this
    }

    SomethingSmallerWithDemandBuilder demandedLevels(Demand level) {
        // todo
        this
    }

    SomethingSmallerWithDemandBuilder adjustedTo(long level) {
        // todo
        this
    }

    SomethingSmallerWithDemandBuilder stronglyAdjustedTo(long level) {
        // todo
        this
    }

    Demand newCallOffDemand(long level) {
        // todo
    }

    Adjustment adjustDemandTo(long level) {
        // todo
    }

    def levelChanged(long previous, long current) {
        // todo
    }

    def reviewRequest(long previousDocumented, long adjustment, long newDocumented) {
        // todo
    }

    def noLevelChanged() {
        // todo
    }
}
