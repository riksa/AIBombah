package org.riksa.bombah



import org.junit.*
import grails.test.mixin.*

@TestFor(BombahClientController)
@Mock(BombahClient)
class BombahClientControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bombahClient/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bombahClientInstanceList.size() == 0
        assert model.bombahClientInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bombahClientInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bombahClientInstance != null
        assert view == '/bombahClient/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bombahClient/show/1'
        assert controller.flash.message != null
        assert BombahClient.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bombahClient/list'


        populateValidParams(params)
        def bombahClient = new BombahClient(params)

        assert bombahClient.save() != null

        params.id = bombahClient.id

        def model = controller.show()

        assert model.bombahClientInstance == bombahClient
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bombahClient/list'


        populateValidParams(params)
        def bombahClient = new BombahClient(params)

        assert bombahClient.save() != null

        params.id = bombahClient.id

        def model = controller.edit()

        assert model.bombahClientInstance == bombahClient
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bombahClient/list'

        response.reset()


        populateValidParams(params)
        def bombahClient = new BombahClient(params)

        assert bombahClient.save() != null

        // test invalid parameters in update
        params.id = bombahClient.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bombahClient/edit"
        assert model.bombahClientInstance != null

        bombahClient.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bombahClient/show/$bombahClient.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bombahClient.clearErrors()

        populateValidParams(params)
        params.id = bombahClient.id
        params.version = -1
        controller.update()

        assert view == "/bombahClient/edit"
        assert model.bombahClientInstance != null
        assert model.bombahClientInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bombahClient/list'

        response.reset()

        populateValidParams(params)
        def bombahClient = new BombahClient(params)

        assert bombahClient.save() != null
        assert BombahClient.count() == 1

        params.id = bombahClient.id

        controller.delete()

        assert BombahClient.count() == 0
        assert BombahClient.get(bombahClient.id) == null
        assert response.redirectedUrl == '/bombahClient/list'
    }
}
