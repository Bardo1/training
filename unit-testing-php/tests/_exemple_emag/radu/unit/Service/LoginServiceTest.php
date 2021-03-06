<?php

namespace App\Tests\Service;


use App\Service\LoginService;
use PHPUnit\Framework\TestCase;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Router;

class LoginServiceTest extends TestCase
{
    /** @var LoginService */
    protected $loginService;

    /** @var Router|\PHPUnit_Framework_MockObject_MockObject */
    protected $routerMock;

    public function setUp()
    {
        parent::setUp(); // TODO: Change the autogenerated stub

        $this->routerMock = static::createMock(Router::class);

        $this->loginService = new LoginService($this->routerMock);
    }

    public function testGetRedirectUri()
    {
        $this->routerMock
            ->expects($this->once())
            ->method('generate');

        $this->loginService->getRedirectUri(new Request(), 'security_check_route');
    }

    public function testGetState()
    {
        $request = new Request();
        $request->request->set('state', 'some_state');

        $this->assertEquals($request->get('state'), $this->loginService->getState($request));
    }
}