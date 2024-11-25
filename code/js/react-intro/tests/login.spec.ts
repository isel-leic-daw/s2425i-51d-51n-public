import { test, expect } from '@playwright/test';

test('can login', async ({ page }) => {
  // when: navigating to the home page
  await page.goto('http://localhost:3000/');

  // then: the page has a link to the 'page 2' page
  const meLink = page.getByRole('link', {name: 'Page 2', exact:true})
  await expect(meLink).toBeVisible()

  // when: navigating to the 'page 2' page
  await meLink.click()

  // then: the login form appears
  const usernameInput = page.getByLabel("Username")
  const passwordInput = page.getByLabel("Password")
  const loginButton = page.getByRole('button')
  await expect(usernameInput).toBeVisible()
  await expect(passwordInput).toBeVisible()
  await expect(loginButton).toBeVisible()

  // when: providing incorrect credentials
  await usernameInput.fill("alice")
  await passwordInput.fill("123")
  await loginButton.click()

  // then: the button get disabled and then enabled again 
  await expect(loginButton).toBeDisabled()
  await expect(loginButton).toBeEnabled()

  // and: the error message appears
  await expect(page.getByText("Invalid username or password")).toBeVisible()

  // and: only the username is preserved
  await expect(usernameInput).toHaveValue("alice")
  await expect(passwordInput).toHaveValue("")

  // when: providing correct credentials
  await usernameInput.fill("alice")
  await passwordInput.fill("1234")
  await loginButton.click()

  // then
  await expect(page.getByText('Hello alice')).toBeVisible()
});